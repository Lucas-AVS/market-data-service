package com.lucasavs.market_data_service.service;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import com.lucasavs.market_data_service.dto.PriceDto;
import com.lucasavs.market_data_service.entity.TradingPair;
import com.lucasavs.market_data_service.event.MarketPriceUpdatedEvent;
import com.lucasavs.market_data_service.mapper.CmcIdMapper;
import com.lucasavs.market_data_service.repository.TradingPairRepository;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PriceIngestorService {

    private static final Logger logger = LoggerFactory.getLogger(PriceIngestorService.class);

    private final TradingPairRepository tradingPairRepository;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;
    private final CloseableHttpClient httpClient;
    private final KafkaTemplate<String, MarketPriceUpdatedEvent> kafkaTemplate;

    private static final String TOPIC_MARKET_PRICE_UPDATED = "market.price.updated";

    @Value("${COIN_MARKET_CAP_API_KEY}")
    private String apiKey;

    @Autowired
    public PriceIngestorService(TradingPairRepository tradingPairRepository,
                                StringRedisTemplate redisTemplate,
                                ObjectMapper objectMapper,
                                KafkaTemplate kafkaTemplate) {
        this.tradingPairRepository = tradingPairRepository;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.httpClient = HttpClients.createDefault();
        this.kafkaTemplate= kafkaTemplate;
    }

    // run in 5 in 5 minutes
    @Scheduled(initialDelay = 10000, fixedRate = 300000)
    public void ingestMarketPrices() {
        String uri = "https://pro-api.coinmarketcap.com/v2/tools/price-conversion";
        List<TradingPair> tradingPairList = this.tradingPairRepository.findAll();

        logger.info("Starting price ingestion for {} trading pairs.", tradingPairList.size());

        for (TradingPair tradingPair : tradingPairList) {
            String baseSymbol = tradingPair.getBaseAsset().getSymbol();
            String quoteSymbol = tradingPair.getQuoteAsset().getSymbol();

            String redisKey = String.format("price:%s-%s", baseSymbol, quoteSymbol);

            List<NameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair("amount", "1"));
            parameters.add(new BasicNameValuePair("id", CmcIdMapper.getId(baseSymbol).toString()));

            String quoteId = CmcIdMapper.getId(quoteSymbol).toString();
            parameters.add(new BasicNameValuePair("convert_id", quoteId));

            try {
                String result = makeAPICall(uri, parameters);
                JsonNode root = objectMapper.readTree(result);

                double priceDouble = root.path("data").path("quote")
                        .path(quoteId).path("price").asDouble();
                BigDecimal price = new BigDecimal(priceDouble);
                Instant timestamp = Instant.now();

                MarketPriceUpdatedEvent event = new MarketPriceUpdatedEvent(
                        redisKey,
                        price,
                        timestamp
                );

                PriceDto dataToCache = new PriceDto(price, timestamp);
                String jsonValue = objectMapper.writeValueAsString(dataToCache);

                redisTemplate.opsForValue().set(redisKey, jsonValue, 10, TimeUnit.MINUTES);
                kafkaTemplate.send(TOPIC_MARKET_PRICE_UPDATED, event);

            } catch (IOException e) {
                logger.error("Error accessing API content for pair {}-{}: {}", baseSymbol, quoteSymbol, e.getMessage());
            } catch (URISyntaxException e) {
                logger.error("Invalid API URI: {}", e.getMessage());
            } catch (Exception e) {
                logger.error("An unexpected error occurred for pair {}-{}: {}", baseSymbol, quoteSymbol, e.getMessage());
            }
        }
        logger.info("Price ingestion finished.");
    }

    private String makeAPICall(String uri, List<NameValuePair> parameters) throws URISyntaxException, IOException {
        String response_content = "";
        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);

        HttpGet request = new HttpGet(query.build());
        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("X-CMC_PRO_API_KEY", this.apiKey);

        CloseableHttpResponse response = this.httpClient.execute(request);

        try {
            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            response.close();
        }
        return response_content;
    }
}
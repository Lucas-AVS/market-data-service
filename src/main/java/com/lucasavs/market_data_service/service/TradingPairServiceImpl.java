package com.lucasavs.market_data_service.service;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import com.lucasavs.market_data_service.dto.PriceDto;
import com.lucasavs.market_data_service.dto.TradingPairDto;
import com.lucasavs.market_data_service.entity.TradingPair;
import com.lucasavs.market_data_service.exception.ResourceNotFoundException;
import com.lucasavs.market_data_service.mapper.TradingPairMapper;
import com.lucasavs.market_data_service.repository.TradingPairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;


@Service
public class TradingPairServiceImpl implements TradingPairService {
    private final TradingPairRepository tradingPairRepository;
    private final TradingPairMapper tradingPairMapper;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public TradingPairServiceImpl(TradingPairRepository tradingPairRepository,
                                  TradingPairMapper tradingPairMapper,
                                  StringRedisTemplate redisTemplate,
                                  ObjectMapper objectMapper) {
        this.tradingPairRepository = tradingPairRepository;
        this.tradingPairMapper = tradingPairMapper;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public TradingPairDto findByBaseAssetSymbolAndQuoteAssetSymbol(String baseAssetSymbol, String quoteAssetSymbol) {
        TradingPair tradingPair = tradingPairRepository
                .findByBaseAssetSymbolAndQuoteAssetSymbol(baseAssetSymbol, quoteAssetSymbol).orElseThrow(
                        () -> new ResourceNotFoundException("Trading pair: " + baseAssetSymbol + "/" + quoteAssetSymbol + " not found."));;

        String redisKey = String.format("price:%s-%s", baseAssetSymbol, quoteAssetSymbol);
        String jsonValue = redisTemplate.opsForValue().get(redisKey);

        BigDecimal currentPrice = null;
        Instant updatedAt = null;
        if (jsonValue != null) {
            try {
                PriceDto cachedData = objectMapper.readValue(jsonValue, PriceDto.class);
                currentPrice = cachedData.price();
                updatedAt = cachedData.timestamp();
            } catch (JacksonException e) {
                System.err.println("Failed to process Redis JSON: " + e.getMessage());
            }
        }

        return tradingPairMapper.toDto(tradingPair, currentPrice, updatedAt);
    }
}

package com.lucasavs.market_data_service.repository.jdbc;

import com.lucasavs.market_data_service.entity.Asset;
import com.lucasavs.market_data_service.entity.TradingPair;
import com.lucasavs.market_data_service.repository.TradingPairRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class TradingPairJdbcRepository implements TradingPairRepository {

    private final JdbcTemplate template;
    public TradingPairJdbcRepository(JdbcTemplate template) {
        this.template = template;
    }

    private static final String SELECT_TRADING_PAIR_BY_BASE_AND_QUOTE_SQL = """
        SELECT base_asset_symbol, quote_asset_symbol
        FROM trading_pairs
        WHERE base_asset_symbol = ? AND quote_asset_symbol = ?
    """;

    private static final String SELECT_ALL_TRADING_PAIRS_SQL = """
        SELECT base_asset_symbol, quote_asset_symbol
        FROM trading_pairs
    """;

    @Override
    public Optional<TradingPair> findByBaseAssetSymbolAndQuoteAssetSymbol(String baseAssetSymbol, String quoteAssetSymbol) {
        return template.query(SELECT_TRADING_PAIR_BY_BASE_AND_QUOTE_SQL,
                tradingPairRowMapper(), baseAssetSymbol, quoteAssetSymbol)
                .stream().findFirst();
    }

    @Override
    public List<TradingPair> findAll() {
        return template.query(SELECT_ALL_TRADING_PAIRS_SQL, tradingPairRowMapper());
    }

    private RowMapper<TradingPair> tradingPairRowMapper() {
        return (rs, rowNum) -> {
            TradingPair tradingPair = new TradingPair();

            Asset baseAsset = new Asset();
            baseAsset.setSymbol(rs.getObject("base_asset_symbol", String.class));
            tradingPair.setBaseAsset(baseAsset);

            Asset quoteAsset = new Asset();
            quoteAsset.setSymbol(rs.getObject("quote_asset_symbol", String.class));
            tradingPair.setQuoteAsset(quoteAsset);

            return tradingPair;
        };
    }
}


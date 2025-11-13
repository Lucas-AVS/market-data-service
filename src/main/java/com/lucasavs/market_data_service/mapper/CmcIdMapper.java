package com.lucasavs.market_data_service.mapper;

import java.util.Map;

// CoinMarketCap ID for a given currency symbol.
public class CmcIdMapper {

    private static final Map<String, Integer> CURRENCY_IDS = Map.of(
            "BTC", 1,
            "ETH", 1027,
            "USD", 2781,
            "BRL", 2783
    );

    public static Integer getId(String symbol) {
        return CURRENCY_IDS.get(symbol);
    }
}
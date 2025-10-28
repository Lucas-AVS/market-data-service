package com.lucasavs.market_data_service.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record TradingPairDto(
        String baseAssetSymbol,
        String quoteAssetSymbol,
        BigDecimal lastKnownPrice,
        Instant priceUpdatedAt
) {}
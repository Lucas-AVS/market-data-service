package com.lucasavs.market_data_service.event;

public record MarketPriceUpdatedEvent(
        String pair,
        java.math.BigDecimal price,
        java.time.Instant timestamp
) {}
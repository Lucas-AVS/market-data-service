package com.lucasavs.market_data_service.mapper;

import com.lucasavs.market_data_service.dto.TradingPairDto;
import com.lucasavs.market_data_service.entity.TradingPair;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Component
public class TradingPairMapper {

    public TradingPairDto toDto(TradingPair entity, BigDecimal price, Instant updatedAt) {
        if (entity == null) {
            return null;
        }

        return new TradingPairDto(
                entity.getBaseAsset().getSymbol(),
                entity.getQuoteAsset().getSymbol(),
                price,
                updatedAt
        );
    }
}
package com.lucasavs.market_data_service.mapper;

import com.lucasavs.market_data_service.dto.TradingPairDto;
import com.lucasavs.market_data_service.entity.TradingPair;
import org.springframework.stereotype.Component;

@Component
public class TradingPairMapper {

    public TradingPairDto toDto(TradingPair entity) {
        if (entity == null) {
            return null;
        }

        return new TradingPairDto(
                entity.getBaseAsset().getSymbol(),
                entity.getQuoteAsset().getSymbol(),
                entity.getLastKnownPrice(),
                entity.getPriceUpdatedAt()
        );
    }
}
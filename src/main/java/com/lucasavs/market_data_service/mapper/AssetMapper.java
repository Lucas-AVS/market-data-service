package com.lucasavs.market_data_service.mapper;

import com.lucasavs.market_data_service.dto.AssetDto;
import com.lucasavs.market_data_service.entity.Asset;
import org.springframework.stereotype.Component;

@Component
public class AssetMapper {
    public AssetDto toDto(Asset asset) {
        AssetDto dto = new AssetDto();
        dto.setSymbol(asset.getSymbol());
        dto.setName(asset.getName());
        dto.setType(asset.getKind());
        dto.setDecimalPlaces(asset.getScale());
        return dto;
    }

}

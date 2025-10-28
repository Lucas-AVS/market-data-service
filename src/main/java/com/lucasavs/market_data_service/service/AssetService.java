package com.lucasavs.market_data_service.service;

import com.lucasavs.market_data_service.dto.AssetDto;

import java.util.List;

public interface AssetService {
    List<AssetDto> findAll();
    AssetDto findBySymbol(String symbol);
}

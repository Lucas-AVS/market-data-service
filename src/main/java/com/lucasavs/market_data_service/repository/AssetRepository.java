package com.lucasavs.market_data_service.repository;

import com.lucasavs.market_data_service.entity.Asset;

import java.util.List;
import java.util.Optional;

public interface AssetRepository {
    Optional<Asset> findBySymbol(String symbol);
    List<Asset> findAll();
}

package com.lucasavs.market_data_service.repository;

import com.lucasavs.market_data_service.entity.TradingPair;

import java.util.List;
import java.util.Optional;

public interface TradingPairRepository {
    Optional<TradingPair> findByBaseAssetSymbolAndQuoteAssetSymbol(String baseAssetSymbol, String quoteAssetSymbol);
    List<TradingPair> findAll();
}

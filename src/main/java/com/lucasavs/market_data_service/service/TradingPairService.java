package com.lucasavs.market_data_service.service;

import com.lucasavs.market_data_service.dto.TradingPairDto;

public interface TradingPairService {
    TradingPairDto findByBaseAssetSymbolAndQuoteAssetSymbol(String baseAssetSymbol, String quoteAssetSymbol);
}

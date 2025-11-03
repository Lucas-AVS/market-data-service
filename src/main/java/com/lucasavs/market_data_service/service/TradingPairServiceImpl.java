package com.lucasavs.market_data_service.service;

import com.lucasavs.market_data_service.dto.TradingPairDto;
import com.lucasavs.market_data_service.entity.TradingPair;
import com.lucasavs.market_data_service.exception.ResourceNotFoundException;
import com.lucasavs.market_data_service.mapper.TradingPairMapper;
import com.lucasavs.market_data_service.repository.TradingPairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TradingPairServiceImpl implements TradingPairService {
    private final TradingPairRepository tradingPairRepository;
    private final TradingPairMapper tradingPairMapper;

    @Autowired
    public TradingPairServiceImpl(TradingPairRepository tradingPairRepository,
                                  TradingPairMapper tradingPairMapper) {
        this.tradingPairRepository = tradingPairRepository;
        this.tradingPairMapper = tradingPairMapper;
    }

    @Override
    public TradingPairDto findByBaseAssetSymbolAndQuoteAssetSymbol(String baseAssetSymbol, String quoteAssetSymbol) {
        Optional<TradingPair> optionalTradingPair = tradingPairRepository
                .findByBaseAssetSymbolAndQuoteAssetSymbol(baseAssetSymbol, quoteAssetSymbol);

        TradingPair tradingPair = optionalTradingPair.orElseThrow(
                () -> new ResourceNotFoundException("Trading pair: " + baseAssetSymbol + "/" + quoteAssetSymbol + " not found."));
        return tradingPairMapper.toDto(tradingPair);
    }
}

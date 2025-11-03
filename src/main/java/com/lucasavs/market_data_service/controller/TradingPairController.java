package com.lucasavs.market_data_service.controller;

import com.lucasavs.market_data_service.dto.TradingPairDto;
import com.lucasavs.market_data_service.service.TradingPairService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trading-pairs")
public class TradingPairController {

    private final TradingPairService tradingPairService;

    public TradingPairController(TradingPairService tradingPairService) {
        this.tradingPairService = tradingPairService;
    }

    @GetMapping("/{baseSymbol}/{quoteSymbol}")
    @ResponseStatus(HttpStatus.OK)
    public TradingPairDto getTradingPair(
            @PathVariable String baseSymbol,
            @PathVariable String quoteSymbol) {

        return tradingPairService.findByBaseAssetSymbolAndQuoteAssetSymbol(baseSymbol, quoteSymbol);
    }
}
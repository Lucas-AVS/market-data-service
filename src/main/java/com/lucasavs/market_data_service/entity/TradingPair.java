package com.lucasavs.market_data_service.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "trading_pairs")
@IdClass(TradingPairId.class)
public class TradingPair {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_asset_symbol", nullable = false)
    private Asset baseAsset;


    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quote_asset_symbol", nullable = false)
    private Asset quoteAsset;

    public TradingPair() {
    }
    public TradingPair(Asset baseAsset, Asset quoteAsset) {
        this.baseAsset = baseAsset;
        this.quoteAsset = quoteAsset;
    }

    public Asset getBaseAsset() {
        return baseAsset;
    }

    public void setBaseAsset(Asset baseAsset) {
        this.baseAsset = baseAsset;
    }

    public Asset getQuoteAsset() {
        return quoteAsset;
    }

    public void setQuoteAsset(Asset quoteAsset) {
        this.quoteAsset = quoteAsset;
    }

    @Override
    public String toString() {
        return "TradingPair{" +
                "baseAsset=" + baseAsset +
                ", quoteAsset=" + quoteAsset +
                '}';
    }
}


package com.lucasavs.market_data_service.entity;

import java.io.Serializable;
import java.util.Objects;

public class TradingPairId implements Serializable {

    private String baseAsset;
    private String quoteAsset;

    public TradingPairId() {
    }

    public TradingPairId(String baseAsset, String quoteAsset) {
        this.baseAsset = baseAsset;
        this.quoteAsset = quoteAsset;
    }

    public String getQuoteAsset() {
        return quoteAsset;
    }

    public void setQuoteAsset(String quoteAsset) {
        this.quoteAsset = quoteAsset;
    }

    public String getBaseAsset() {
        return baseAsset;
    }

    public void setBaseAsset(String baseAsset) {
        this.baseAsset = baseAsset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradingPairId that = (TradingPairId) o;
        return Objects.equals(baseAsset, that.baseAsset) &&
                Objects.equals(quoteAsset, that.quoteAsset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseAsset, quoteAsset);
    }
}
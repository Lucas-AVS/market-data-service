package com.lucasavs.market_data_service.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

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

    @Column(name = "last_known_price")
    private BigDecimal lastKnownPrice;

    @UpdateTimestamp
    @Column(name = "price_updated_at", nullable = false)
    private Instant priceUpdatedAt;

    public TradingPair() {
    }
    public TradingPair(Asset baseAsset, Asset quoteAsset, BigDecimal lastKnownPrice, Instant priceUpdatedAt) {
        this.baseAsset = baseAsset;
        this.quoteAsset = quoteAsset;
        this.lastKnownPrice = lastKnownPrice;
        this.priceUpdatedAt = priceUpdatedAt;
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

    public BigDecimal getLastKnownPrice() {
        return lastKnownPrice;
    }

    public void setLastKnownPrice(BigDecimal lastKnownPrice) {
        this.lastKnownPrice = lastKnownPrice;
    }

    public Instant getPriceUpdatedAt() {
        return priceUpdatedAt;
    }

    public void setPriceUpdatedAt(Instant priceUpdatedAt) {
        this.priceUpdatedAt = priceUpdatedAt;
    }

    @Override
    public String toString() {
        return "TradingPair{" +
                "baseAsset=" + baseAsset +
                ", quoteAsset=" + quoteAsset +
                ", lastKnownPrice=" + lastKnownPrice +
                ", priceUpdatedAt=" + priceUpdatedAt +
                '}';
    }
}


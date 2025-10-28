package com.lucasavs.market_data_service.repository.jpa;

import com.lucasavs.market_data_service.entity.TradingPair;
import com.lucasavs.market_data_service.repository.TradingPairRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface TradingPairJpaRepository extends JpaRepository<TradingPair, String>, TradingPairRepository {
}

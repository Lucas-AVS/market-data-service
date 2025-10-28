package com.lucasavs.market_data_service.repository.jpa;

import com.lucasavs.market_data_service.entity.Asset;
import com.lucasavs.market_data_service.repository.AssetRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface AssetJpaRepository extends JpaRepository<Asset, String>, AssetRepository {
    // custom query ex: Optional<User> findByEmail(String email);
}
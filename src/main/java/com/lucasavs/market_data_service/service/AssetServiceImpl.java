package com.lucasavs.market_data_service.service;

import com.lucasavs.market_data_service.dto.AssetDto;
import com.lucasavs.market_data_service.entity.Asset;
import com.lucasavs.market_data_service.exception.ResourceNotFoundException;
import com.lucasavs.market_data_service.mapper.AssetMapper;
import com.lucasavs.market_data_service.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final AssetMapper assetMapper;

    @Autowired
    public AssetServiceImpl(AssetRepository assetRepository, AssetMapper assetMapper) {
        this.assetRepository = assetRepository;
        this.assetMapper = assetMapper;
    }

    @Override
    public List<AssetDto> findAll() {
        List<Asset> assetList = assetRepository.findAll();
        return assetList
                .stream()
                .map(assetMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AssetDto findBySymbol(String symbol) {
        Optional<Asset> optionalAsset = assetRepository.findBySymbol(symbol);

        Asset asset = optionalAsset.orElseThrow(() -> new ResourceNotFoundException(symbol + " does not exist in this exchange"));
        return assetMapper.toDto(asset);
    }
}

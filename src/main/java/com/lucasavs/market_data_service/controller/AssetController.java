package com.lucasavs.market_data_service.controller;

import com.lucasavs.market_data_service.dto.AssetDto;
import com.lucasavs.market_data_service.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assets")
public class AssetController {
    private final AssetService assetService;

    @Autowired
    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AssetDto> findAllAssets() {
        return assetService.findAll();
    }

    @GetMapping("/{assetSymbol}")
    @ResponseStatus(HttpStatus.OK)
    public AssetDto getAsset(@PathVariable String assetSymbol) {
        return assetService.findBySymbol(assetSymbol);
    }
}

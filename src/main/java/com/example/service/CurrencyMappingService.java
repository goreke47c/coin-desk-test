package com.example.service;

import com.example.dto.BpiData;
import com.example.dto.CurrencyData;
import com.example.dto.CurrentPrice;
import com.example.dto.CurrentPriceResponse;
import com.example.entity.CurrencyMapping;
import com.example.repo.CurrencyMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CurrencyMappingService {

    private final CurrencyMappingRepository currencyMappingRepository;

    private final CoinDeskService coinDeskService;

    @Autowired
    public CurrencyMappingService(CurrencyMappingRepository currencyMappingRepository, CoinDeskService coinDeskService) {
        this.currencyMappingRepository = currencyMappingRepository;
        this.coinDeskService = coinDeskService;
    }

    public List<CurrencyMapping> getAllCurrencyMappings() {
        return currencyMappingRepository.findAll();
    }

    public CurrencyMapping getCurrencyMappingByCode(String currencyCode) {
        return currencyMappingRepository.findById(currencyCode).orElse(null);
    }

    public CurrencyMapping saveCurrencyMapping(CurrencyMapping currencyMapping) {
        return currencyMappingRepository.save(currencyMapping);
    }

    public void deleteCurrencyMapping(String currencyCode) {
        currencyMappingRepository.deleteById(currencyCode);
    }

    public CurrencyMapping updateCurrencyMapping(String currencyCode, CurrencyMapping updatedMapping) {
        CurrencyMapping existingMapping = currencyMappingRepository.findById(currencyCode).orElse(null);
        if (existingMapping != null) {
            if (updatedMapping.getCurrencyName() != null) {
                existingMapping.setCurrencyName(updatedMapping.getCurrencyName());
            }
            if (updatedMapping.getCurrencyChName() != null) {
                existingMapping.setCurrencyChName(updatedMapping.getCurrencyChName());
            }
            if (updatedMapping.getRateText() != null) {
                existingMapping.setRateText(updatedMapping.getRateText());
            }
            if (updatedMapping.getRateFloat() != null) {
                existingMapping.setRateFloat(updatedMapping.getRateFloat());
            }
            if (updatedMapping.getSymbol() != null) {
                existingMapping.setSymbol(updatedMapping.getSymbol());
            }
            existingMapping.setLastUpdated(new Date());
            return currencyMappingRepository.save(existingMapping);
        }
        return null;
    }

    public CurrentPriceResponse genCurrentPriceResponse() {
        Date updateTime = new Date();
        CurrentPriceResponse response = new CurrentPriceResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        CurrentPrice currentPrice = coinDeskService.getCurrentPrice();
        CurrencyMapping usdMapping = convertBpiDataToCurrencyMapping(currentPrice.getBpi().getUsd(), updateTime);
        CurrencyMapping eurMapping = convertBpiDataToCurrencyMapping(currentPrice.getBpi().getEur(), updateTime);
        CurrencyMapping gbpMapping = convertBpiDataToCurrencyMapping(currentPrice.getBpi().getGbp(), updateTime);
        currencyMappingRepository.save(usdMapping);
        currencyMappingRepository.save(eurMapping);
        currencyMappingRepository.save(gbpMapping);

        ArrayList<CurrencyData> currencyDataList = new ArrayList<>();
        currencyDataList.add(convertMappingToData(usdMapping));
        currencyDataList.add(convertMappingToData(eurMapping));
        currencyDataList.add(convertMappingToData(gbpMapping));

        response.setCurrency(currencyDataList);
        response.setUpdateTime(sdf.format(updateTime));
        return response;
    }

    private CurrencyMapping convertBpiDataToCurrencyMapping(BpiData bpiData, Date updateTime) {
        CurrencyMapping currencyMapping = currencyMappingRepository.findById(bpiData.getCode()).orElse(new CurrencyMapping());
        currencyMapping.setCurrencyCode(bpiData.getCode());
        currencyMapping.setCurrencyName(bpiData.getDescription());
        currencyMapping.setSymbol(bpiData.getSymbol());
        currencyMapping.setRateText(bpiData.getRate());
        currencyMapping.setRateFloat(BigDecimal.valueOf(bpiData.getRateFloat()));
        currencyMapping.setLastUpdated(updateTime);

        return currencyMapping;
    }

    private CurrencyData convertMappingToData(CurrencyMapping mapping) {
        CurrencyData currencyData = new CurrencyData();
        currencyData.setCode(mapping.getCurrencyCode());
        currencyData.setCurrencyName(mapping.getCurrencyName());
        currencyData.setCurrencyChName(mapping.getCurrencyChName());
        currencyData.setExchangeRate(mapping.getRateFloat().doubleValue());
        return currencyData;
    }
}

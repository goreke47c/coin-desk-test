package com.example.controller;

import com.example.dto.CurrentPriceResponse;
import com.example.entity.CurrencyMapping;
import com.example.service.CurrencyMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    private final CurrencyMappingService currencyMappingService;

    @Autowired
    public CurrencyController(CurrencyMappingService currencyMappingService) {
        this.currencyMappingService = currencyMappingService;
    }

    /**
     * 呼叫coindesk api進行資料轉換並組成新的api
     *
     * @return CurrentPriceResponse
     */
    @GetMapping("/currentprice.json")
    public ResponseEntity<CurrentPriceResponse> currentPriceJson() {
        return new ResponseEntity<>(currencyMappingService.genCurrentPriceResponse(), HttpStatus.OK);
    }

    /**
     * 取得所有幣別資料
     *
     * @return List<CurrencyMapping>
     */
    @GetMapping
    public ResponseEntity<List<CurrencyMapping>> getAllCurrencyMappings() {
        List<CurrencyMapping> currencyMappings = currencyMappingService.getAllCurrencyMappings();
        return new ResponseEntity<>(currencyMappings, HttpStatus.OK);
    }

    /**
     * 取得指定幣別資料
     *
     * @param currencyCode 幣別編號
     * @return CurrencyMapping
     */
    @GetMapping("/{currencyCode}")
    public ResponseEntity<CurrencyMapping> getCurrencyMappingByCode(@PathVariable String currencyCode) {
        CurrencyMapping currencyMapping = currencyMappingService.getCurrencyMappingByCode(currencyCode);

        if (currencyMapping != null) {
            return new ResponseEntity<>(currencyMapping, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 新增幣別資料
     *
     * @param currencyMapping 資料欄位
     * @return CurrencyMapping
     */
    @PostMapping
    public ResponseEntity<CurrencyMapping> createCurrencyMapping(@RequestBody CurrencyMapping currencyMapping) {
        CurrencyMapping createdMapping = currencyMappingService.saveCurrencyMapping(currencyMapping);
        return new ResponseEntity<>(createdMapping, HttpStatus.CREATED);
    }

    /**
     * 更新幣別資料
     *
     * @param currencyCode   幣別編號
     * @param updatedMapping 更新欄位
     * @return CurrencyMapping
     */
    @PutMapping("/{currencyCode}")
    public ResponseEntity<CurrencyMapping> updateCurrencyMapping(
            @PathVariable String currencyCode, @RequestBody CurrencyMapping updatedMapping) {
        CurrencyMapping currencyMapping = currencyMappingService.updateCurrencyMapping(currencyCode, updatedMapping);
        if (currencyMapping != null) {
            return new ResponseEntity<>(currencyMapping, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 刪除幣別資料
     *
     * @param currencyCode 幣別編號
     * @return NO_CONTENT
     */
    @DeleteMapping("/{currencyCode}")
    public ResponseEntity<Void> deleteCurrencyMapping(@PathVariable String currencyCode) {
        currencyMappingService.deleteCurrencyMapping(currencyCode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

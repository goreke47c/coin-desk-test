package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrencyData {

    private String code;

    @JsonProperty("currency_name")
    private String currencyName;

    @JsonProperty("currency_ch_name")
    private String currencyChName;

    @JsonProperty("exchange_rate")
    private double exchangeRate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyChName() {
        return currencyChName;
    }

    public void setCurrencyChName(String currencyChName) {
        this.currencyChName = currencyChName;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}

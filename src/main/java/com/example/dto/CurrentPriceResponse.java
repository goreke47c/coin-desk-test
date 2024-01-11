package com.example.dto;

import java.util.ArrayList;

public class CurrentPriceResponse {
    private String updateTime;
    private ArrayList<CurrencyData> currency;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public ArrayList<CurrencyData> getCurrency() {
        return currency;
    }

    public void setCurrency(ArrayList<CurrencyData> currency) {
        this.currency = currency;
    }
}

package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BpiInfo {

    @JsonProperty("USD")
    private BpiData usd;

    @JsonProperty("GBP")
    private BpiData gbp;

    @JsonProperty("EUR")
    private BpiData eur;

    public BpiData getUsd() {
        return usd;
    }

    public void setUsd(BpiData usd) {
        this.usd = usd;
    }

    public BpiData getGbp() {
        return gbp;
    }

    public void setGbp(BpiData gbp) {
        this.gbp = gbp;
    }

    public BpiData getEur() {
        return eur;
    }

    public void setEur(BpiData eur) {
        this.eur = eur;
    }
}

package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class CurrencyMapping {

    @Id
    @Column(name = "currency_code", length = 3)
    private String currencyCode;

    @Column(name = "currency_name", length = 50)
    private String currencyName;

    @Column(name = "currency_ch_name", length = 50)
    private String currencyChName;

    @Column(name = "rate_text", length = 20)
    private String rateText;

    @Column(name = "rate_float", precision = 10, scale = 4)
    private BigDecimal rateFloat;

    @Column(name = "symbol", length = 10)
    private String symbol;

    @Column(name = "last_updated")
    private Date lastUpdated;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
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

    public String getRateText() {
        return rateText;
    }

    public void setRateText(String rateText) {
        this.rateText = rateText;
    }

    public BigDecimal getRateFloat() {
        return rateFloat;
    }

    public void setRateFloat(BigDecimal rateFloat) {
        this.rateFloat = rateFloat;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}

package com.example.dto;

public class CurrentPrice {

    private TimeInfo time;

    private String disclaimer;

    private String chartName;

    private BpiInfo bpi;

    public TimeInfo getTime() {
        return time;
    }

    public void setTime(TimeInfo time) {
        this.time = time;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public BpiInfo getBpi() {
        return bpi;
    }

    public void setBpi(BpiInfo bpi) {
        this.bpi = bpi;
    }
}

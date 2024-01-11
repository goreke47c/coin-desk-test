package com.example.service;

import com.example.dto.CurrentPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CoinDeskService {

    private final RestTemplate restTemplate;

    @Autowired
    public CoinDeskService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CurrentPrice getCurrentPrice() {
        String apiUrl = "https://api.coindesk.com/v1/bpi/currentprice.json";
        return restTemplate.getForEntity(apiUrl, CurrentPrice.class).getBody();
    }
}

package com.example.controller;

import com.example.app.App;
import com.example.dto.CurrentPrice;
import com.example.entity.CurrencyMapping;
import com.example.service.CoinDeskService;
import com.example.service.CurrencyMappingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Date;

@SpringBootTest(classes = App.class)
public class CurrencyControllerTest {

    Logger logger = LoggerFactory.getLogger(CurrencyControllerTest.class);
    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    CoinDeskService coinDeskService;

    @Autowired
    CurrencyMappingService currencyMappingService;

    @BeforeEach
    protected void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        initialData();
    }

    protected void initialData() {
        CurrencyMapping usd = new CurrencyMapping();
        usd.setCurrencyCode("USD");
        usd.setCurrencyChName("美元");
        usd.setCurrencyName("United States Dollar");
        usd.setRateText("46,419.405");
        usd.setRateFloat(BigDecimal.valueOf(46419.4054));
        usd.setSymbol("&#36;");
        usd.setLastUpdated(new Date());
        CurrencyMapping eur = new CurrencyMapping();
        eur.setCurrencyCode("EUR");
        eur.setCurrencyChName("歐元");
        eur.setCurrencyName("Euro");
        eur.setRateText("42,295.134");
        eur.setRateFloat(BigDecimal.valueOf(42295.1341));
        eur.setSymbol("&euro;");
        eur.setLastUpdated(new Date());
        CurrencyMapping gbp = new CurrencyMapping();
        gbp.setCurrencyCode("GBP");
        gbp.setCurrencyChName("英鎊");
        gbp.setCurrencyName("British Pound Sterling");
        gbp.setRateText("36,372.297");
        gbp.setRateFloat(BigDecimal.valueOf(36372.2965));
        gbp.setSymbol("&pound;");
        gbp.setLastUpdated(new Date());
        currencyMappingService.saveCurrencyMapping(usd);
        currencyMappingService.saveCurrencyMapping(eur);
        currencyMappingService.saveCurrencyMapping(gbp);
    }

    /**
     * 1.測試呼叫查詢幣別對應表資料API並顯示內容
     */
    @Test
    public void testGetCurrencyMappingByCode() throws Exception {
        logger.info("testGetCurrencyMappingByCode start");
        String uri = "/api/currency/USD";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String content = mvcResult.getResponse().getContentAsString(Charset.defaultCharset());
        logger.info("testGetCurrencyMappingByCode response: " + content);
        ObjectMapper om = new ObjectMapper();
        CurrencyMapping responseCurrency = om.readValue(content, CurrencyMapping.class);
        Assertions.assertEquals("USD", responseCurrency.getCurrencyCode());
        logger.info("testGetCurrencyMappingByCode end");
    }

    /**
     * 2.測試呼叫新增幣別對應表資料API
     */
    @Test
    public void testCreateCurrencyMapping() throws Exception {
        logger.info("testCreateCurrencyMapping start");
        ObjectMapper om = new ObjectMapper();
        CurrencyMapping usd = new CurrencyMapping();
        usd.setCurrencyCode("USD");
        usd.setCurrencyChName("美元");
        usd.setCurrencyName("United States Dollar");
        usd.setRateText("40,000.0004");
        usd.setRateFloat(BigDecimal.valueOf(40000.0004));
        usd.setSymbol("&#36;");
        usd.setLastUpdated(new Date());
        String jsonBody = om.writeValueAsString(usd);
        String uri = "/api/currency";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri).content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Assertions.assertEquals(201, mvcResult.getResponse().getStatus());
        logger.info("testCreateCurrencyMapping end");
    }

    /**
     * 3.測試呼叫更新幣別對應表資料API並顯示內容
     */
    @Test
    public void testUpdateCurrencyMapping() throws Exception {
        logger.info("testUpdateCurrencyMapping start");
        ObjectMapper om = new ObjectMapper();
        CurrencyMapping usd = new CurrencyMapping();
        usd.setCurrencyChName("美元");
        usd.setLastUpdated(new Date());
        usd.setRateText("40,000.0003");
        usd.setRateFloat(BigDecimal.valueOf(40000.0003));
        String jsonBody = om.writeValueAsString(usd);
        String uri = "/api/currency/USD";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri).content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String content = mvcResult.getResponse().getContentAsString(Charset.defaultCharset());
        logger.info("testUpdateCurrencyMapping response: " + content);
        logger.info("testUpdateCurrencyMapping end");
    }

    /**
     * 4.測試呼叫更新幣別對應表資料API
     */
    @Test
    public void testDeleteCurrencyMapping() throws Exception {
        logger.info("testDeleteCurrencyMapping start");
        String uri = "/api/currency/USD";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Assertions.assertEquals(204, mvcResult.getResponse().getStatus());
        logger.info("testDeleteCurrencyMapping end");
    }

    /**
     * 5.測試呼叫coindesk API並顯示內容
     */
    @Test
    public void testCoindeskApi() throws Exception {
        logger.info("testCoindeskApi start");
        CurrentPrice coindeskResult = coinDeskService.getCurrentPrice();
        Assertions.assertNotNull(coindeskResult);
        ObjectMapper om = new ObjectMapper();
        logger.info("coindesk API reponse: " + om.writeValueAsString(coindeskResult));
        logger.info("testCoindeskApi end");
    }

    /**
     * 6.測試呼叫資料轉換的API並顯示內容
     */
    @Test
    public void testCurrentpriceJson() throws Exception {
        logger.info("testCurrentpriceJson start");
        String uri = "/api/currency/currentprice.json";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String content = mvcResult.getResponse().getContentAsString(Charset.defaultCharset());
        logger.info("currentprice.json response: " + content);
        logger.info("testCurrentpriceJson end");
    }

}

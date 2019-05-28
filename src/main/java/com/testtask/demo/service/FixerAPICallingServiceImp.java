package com.testtask.demo.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.testtask.demo.appunits.AllCurrenciesUnit;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@Log4j2
public class FixerAPICallingServiceImp implements FixerAPICallingService {

    @Override
    public AllCurrenciesUnit getResponse(String url) {
        ObjectMapper objectMapper = new ObjectMapper();

        HttpResponse<JsonNode> jsonResponse;
        AllCurrenciesUnit allСurrencies = null;

        try {
            jsonResponse = Unirest.get(url).asJson();
            String s = jsonResponse.getBody().toString();
            Map<String, Object> map
                    = objectMapper.readValue(s, new TypeReference<Map<String, Object>>() {
            });

            allСurrencies = objectMapper
                    .readValue(jsonResponse.getBody().toString(), AllCurrenciesUnit.class);
        } catch (IOException | UnirestException e) {

            log.error("Cannot get response from external API!");
        }
        log.info(allСurrencies);

        return allСurrencies;
    }

    @Override
    public Map<String, Float> createData(String url) {


        AllCurrenciesUnit response = getResponse(url);

        Map<String, Float> rates = response.getRates();

        log.info(rates);

        return rates;
    }
}

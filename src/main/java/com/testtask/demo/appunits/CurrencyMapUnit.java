package com.testtask.demo.appunits;

import com.testtask.demo.service.FixerAPICallingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;

@EnableScheduling
@Log4j2
public class CurrencyMapUnit {

    private Map<String, Float> allCurrency;

    private FixerAPICallingService apiCallingService;

    @Value("${external.app.url}")
    private String url;

    @Autowired
    public void setApiCallingService(FixerAPICallingService apiCallingService) {
        this.apiCallingService = apiCallingService;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public Map<String, Float> getAllCurrency() {
        return allCurrency;
    }

    @Scheduled(fixedDelay = 1000000)//16 minutes
    public void setAllCurrencies() {
        try {
            this.allCurrency = apiCallingService.createData(url);

        } catch (NullPointerException e) {

            log.warn("Cannot initialise map");
        }
    }
}

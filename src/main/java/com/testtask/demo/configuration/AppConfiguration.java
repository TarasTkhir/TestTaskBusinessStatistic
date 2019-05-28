package com.testtask.demo.configuration;


import com.testtask.demo.appunits.CurrencyMapUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
public class AppConfiguration {

    @Bean
    public CurrencyMapUnit allСurrencies() {

        return new CurrencyMapUnit();
    }
}

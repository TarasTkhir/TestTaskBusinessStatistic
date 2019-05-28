package com.testtask.demo.controller;

import com.testtask.demo.appunits.CurrencyMapUnit;
import com.testtask.demo.service.FixerAPICallingService;
import com.testtask.demo.service.PurchaseService;
import com.testtask.demo.testData.AddTestUnits;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class PostPurchaseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PurchaseService purchaseService;

    @MockBean
    FixerAPICallingService fixerAPICallingService;

    @MockBean
    CurrencyMapUnit currencyMapUnit;


@Test
public void givenMockMvc_whenReportByYearMappingCalled_thenSavePurchaseAndReturnPurchaseListWithAllPurchaseOfThisDate_Positive() throws Exception {

    when(currencyMapUnit.getAllCurrency()).thenReturn(AddTestUnits.generateTestMapOfFewMappedCurrencies());
    purchaseService.setAllСurrencies(currencyMapUnit);


    this.mockMvc.perform(post("/purchase/2012-02-02/20/USD/whoCare")).andDo(print())
            .andExpect(status().isAccepted())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.[0].date").value("2012-02-02"))
            .andExpect(jsonPath("$.[0].price").value(20.0))
            .andExpect(jsonPath("$.[0].currency").value("USD"))
            .andExpect(jsonPath("$.[0].productName").value("whoCare"));
}
    @Test
public void givenMockMvcAndInvalidPrice_whenPurchaseMappingCalled_thenThrowWrongDataExceptionAndReturnExceptionWire_Negative() throws Exception {

    when(currencyMapUnit.getAllCurrency()).thenReturn(AddTestUnits.generateTestMapOfFewMappedCurrencies());
    purchaseService.setAllСurrencies(currencyMapUnit);


    this.mockMvc.perform(post("/purchase/2019-09-09/wrongPrice/USD/whoCare")).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.status").value("400 BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("Invalid price"));
}
    @Test
    public void givenMockMvcAndCurrencyThatNotExist_whenPurchaseMappingCalled_thenThrowWrongDataExceptionAndReturnExceptionWire_Negative_Negative() throws Exception {

        when(currencyMapUnit.getAllCurrency()).thenReturn(AddTestUnits.generateTestMapOfFewMappedCurrencies());
        purchaseService.setAllСurrencies(currencyMapUnit);


        this.mockMvc.perform(post("/purchase/2019-09-09/24/XXX/whoCare")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("400 BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Currency not exist, input valid currency."));
    }
    @Test
    public void givenMockMvcAndYearBiggerThanCurrent_whenReportByYearMappingCalled_thenThrowWrongDataExceptionAndReturnExceptionWire_Negative() throws Exception {

        when(currencyMapUnit.getAllCurrency()).thenReturn(AddTestUnits.generateTestMapOfFewMappedCurrencies());
        purchaseService.setAllСurrencies(currencyMapUnit);

        int year = LocalDate.now().plusYears(1).getYear();

        this.mockMvc.perform(post("/purchase/"+year+"-08-08/322/USD/whoCare")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("400 BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Year must be equals or less than current year"));
    }
    @Test
    public void givenMockMvcAndWrongYearFormat_whenReportByYearMappingCalled_thenThrowWrongDataExceptionAndReturnExceptionWire_Negative() throws Exception {

        when(currencyMapUnit.getAllCurrency()).thenReturn(AddTestUnits.generateTestMapOfFewMappedCurrencies());
        purchaseService.setAllСurrencies(currencyMapUnit);


        this.mockMvc.perform(post("/purchase/201F-03-0g/322/UAH/whoCare")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("400 BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Wrong format of date or invalid date. Must be: YYYY-MM-DD (Example: 2015-03-25)"));
    }


}
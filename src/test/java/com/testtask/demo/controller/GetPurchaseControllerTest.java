package com.testtask.demo.controller;

import com.testtask.demo.appunits.CurrencyMapUnit;
import com.testtask.demo.domain.Purchase;
import com.testtask.demo.repository.PurchaseRepository;
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

import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class GetPurchaseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    PurchaseService purchaseService;

    @MockBean
    FixerAPICallingService fixerAPICallingService;

    @MockBean
    CurrencyMapUnit currencyMapUnit;


    @Test
    public void givenMockMvc_whenGetInfoForStartMappingCalled_thenReturnInstructionWireWithInstructionAndMapOfCurrencies_Positive() throws Exception {

        when(currencyMapUnit.getAllCurrency()).thenReturn(AddTestUnits.generateTestMapOfFewMappedCurrencies());
        purchaseService.setAllСurrencies(currencyMapUnit);

        this.mockMvc.perform(get("/")).andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.instruction").isArray())
                .andExpect(jsonPath("$.currency").isMap())
                .andExpect(jsonPath("$.instruction").isNotEmpty())
                .andExpect(jsonPath("$.currency").isNotEmpty());

    }

    @Test
    public void givenMockMvcAndNotValidCurrency_whenReportByYearMappingCalled_thenThrowWrongDataExceptionAndReturnExceptionWire_Negative() throws Exception {

        when(currencyMapUnit.getAllCurrency()).thenReturn(AddTestUnits.generateTestMapOfFewMappedCurrencies());
        purchaseService.setAllСurrencies(currencyMapUnit);

        this.mockMvc.perform(get("/report/2018/XXX")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("400 BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Not valid currency! Enter valid one."));
    }

    @Test
    public void givenMockMvcAndYearWithNoRecords_whenReportByYearMappingCalled_thenThrowNoPurchaseInDatabaseExceptionAndReturnExceptionWire_Negative() throws Exception {

        when(currencyMapUnit.getAllCurrency()).thenReturn(AddTestUnits.generateTestMapOfFewMappedCurrencies());
        purchaseService.setAllСurrencies(currencyMapUnit);


        this.mockMvc.perform(get("/report/1000/USD")).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("404 NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("No purchases was found for this year."));
    }

    @Test
    public void givenMockMvcAndTestCurrencyMap_whenReportByYearMappingCalled_thenReturnReportWireWithResult_Positive() throws Exception {

        when(currencyMapUnit.getAllCurrency()).thenReturn(AddTestUnits.generateTestMapOfFewMappedCurrencies());
        purchaseService.setAllСurrencies(currencyMapUnit);

        List<Purchase> purchases = AddTestUnits.generateListOfTestPurchases();
        purchases.forEach(purchase->purchaseRepository.save(purchase));

        this.mockMvc.perform(get("/report/2019/uah")).andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.year").value("2019"))
                .andExpect(jsonPath("$.currency").value("UAH"))
                .andExpect(jsonPath("$.totalIncome").isNumber())
                .andExpect(jsonPath("$.totalIncome").isNotEmpty())
                .andExpect(jsonPath("$.totalIncome").value(60));
    }
}
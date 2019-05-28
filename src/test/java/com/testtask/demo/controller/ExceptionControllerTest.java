package com.testtask.demo.controller;

import com.testtask.demo.appunits.CurrencyMapUnit;
import com.testtask.demo.repository.PurchaseRepository;
import com.testtask.demo.service.FixerAPICallingService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class ExceptionControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    PurchaseRepository purchaseRepository;

    @MockBean
    FixerAPICallingService fixerAPICallingService;

    @MockBean
    CurrencyMapUnit currencyMapUnit;


    @Test
    public void givenDateWithNoExistingRecords_whenCallCleanAllByDateMapping_thenHandleNoPurchaseInDatabaseExceptionAndReturnExceptionWire() throws Exception {

        mockMvc.perform(delete("/clear/2015-02-02")).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("404 NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("No purchases was found to delete by date: 2015-02-02" ));
    }

    @Test
    public void givenToBigYear_whenCallReportByYearMapping_thenHandleWrongDataExceptionAndReturnExceptionWire() throws Exception {

        mockMvc.perform(get("/report/2025/USD")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("400 BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Year must be equals or less than current year"));
    }

    @Test
    public void givenTooLongDate_whenCallCleanAllByDateMapping_thenHandleExceptionAndReturnExceptionWire() throws Exception {

        mockMvc.perform(delete("/clear/2015635-02-02123")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("400 BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("deleteByDate.date: Not valid date"));
    }


}
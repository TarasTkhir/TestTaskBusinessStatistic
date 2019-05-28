package com.testtask.demo.controller;

import com.testtask.demo.appunits.CurrencyMapUnit;
import com.testtask.demo.repository.PurchaseRepository;
import com.testtask.demo.service.FixerAPICallingService;
import com.testtask.demo.testData.AddTestUnits;
import org.junit.After;
import org.junit.Before;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class DeletePurchaseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PurchaseRepository purchaseRepository;

    @MockBean
    FixerAPICallingService fixerAPICallingService;

    @MockBean
    CurrencyMapUnit currencyMapUnit;

    @Before
    public void setUp() throws Exception {

        AddTestUnits.generateTestPurchases(purchaseRepository);
    }

        @After
    public void tearDown() throws Exception {

        purchaseRepository.deleteAll();
    }


    @Test
    public void givenMockMvc_whenClearPurchasesByDateMappingCalled_thenReturnDeletedWireWithDeletedRecordsEndStatusDELETED_Positive() throws Exception {

        this.mockMvc.perform(delete("/clear/2019-08-08")).andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("D_E_L_E_T_E_D"))
                .andExpect(jsonPath("$.date").value("2019-08-08"))
                .andExpect(jsonPath("$.purchases").isNotEmpty())
                .andExpect(jsonPath("$.purchases").isArray());
    }

    @Test
    public void givenMockMvc_whenClearPurchasesByDateMappingCalledWithWrongDate_thenReturnExceptionWireWithMessage_Negative() throws Exception {

        this.mockMvc.perform(delete("/clear/20159-8-08")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("400 BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Wrong format of date or invalid date. Must be: YYYY-MM-DD (Example: 2015-03-25)"));

    }

    @Test
    public void givenMockMvc_whenClearAllMappingCalled_thenReturnDeletedWireWithMessage_Negative() throws Exception {

        LocalDate now = LocalDate.now();
        this.mockMvc.perform(delete("/clear/all")).andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("All records D_E_L_E_T_E_D"))
                .andExpect(jsonPath("$.date").value(now.toString()))
                .andExpect(jsonPath("$.purchases").isEmpty())
                .andExpect(jsonPath("$.purchases").isArray());
    }
}
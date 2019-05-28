package com.testtask.demo.appunits;

import com.testtask.demo.service.FixerAPICallingService;
import com.testtask.demo.testData.AddTestUnits;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CurrencyMapUnitTest {

    @InjectMocks
    CurrencyMapUnit currencyMapUnit;

    @Mock
    FixerAPICallingService apiCallingService;

    @Before
    public void setUp() throws Exception {
        apiCallingService = Mockito.mock(FixerAPICallingService.class);
        currencyMapUnit = new CurrencyMapUnit();
    }


    @Test
    public void givenMockedService_whenSetAllCurrenciesCalled_thenSetAllCurrencies() {

        //given
        String url = "url";
        currencyMapUnit.setUrl(url);
        when(apiCallingService.createData(url)).thenReturn(AddTestUnits.generateTestMapOfFewMappedCurrencies());
        this.currencyMapUnit.setApiCallingService(this.apiCallingService);

        //when
        currencyMapUnit.setAllCurrencies();

        //then
        assertEquals(AddTestUnits.generateTestMapOfFewMappedCurrencies()
                .size(), currencyMapUnit.getAllCurrency().size());
    }

    @Test
    public void givenMockedService_whenSetAllCurrenciesCalled_thenSetAllCurrencies_Negative() {

        //given
        String url = "url";
        currencyMapUnit.setUrl(url);
        when(apiCallingService.createData(url)).thenReturn(null);
        this.currencyMapUnit.setApiCallingService(this.apiCallingService);

        //when
        currencyMapUnit.setAllCurrencies();

        //then
        assertEquals(null, currencyMapUnit.getAllCurrency());
    }
}

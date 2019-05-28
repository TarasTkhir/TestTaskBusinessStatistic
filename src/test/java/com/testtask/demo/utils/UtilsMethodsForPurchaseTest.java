package com.testtask.demo.utils;

import com.testtask.demo.domain.Purchase;
import com.testtask.demo.testData.AddTestUnits;
import com.testtask.demo.wire.ReportWire;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class UtilsMethodsForPurchaseTest {

    Map<String, Float> testMap;

    List<Purchase> testPurchases;

    @Before
    public void setUp() throws Exception {

        testMap = AddTestUnits.generateTestMapOfFewMappedCurrencies();
        testPurchases = AddTestUnits.generateListOfTestPurchases();
    }

    @Test
    public void givenTestData_whenCalculateCurrencyForReport_thenReturnReportWireWithCorrectResult() {
        //given
        Integer year = 2019;
        String currency = "UAH";

        //when
        ReportWire reportWire = UtilsMethodsForPurchase
                .calculateCurrencyForReport(this.testMap, this.testPurchases, currency, year);

        //then
        assertEquals(year, reportWire.getYear());
        assertEquals(currency, reportWire.getCurrency());
        assertEquals(60.00, reportWire.getTotalIncome(), 2);
    }

    @Test(expected = NullPointerException.class)
    public void givenPurchaseWithNotExistingCurrency_whenCalculateCurrencyForReport_thenReturnException_Negative() {

        //given
        Integer year = 2019;
        String currency = "UAH";
        this.testPurchases.add(new Purchase(50,
                LocalDate.of(2019, 8, 8), 20.00, "XXX", "GoldPen"));

        //when
        UtilsMethodsForPurchase
                .calculateCurrencyForReport(this.testMap, this.testPurchases, currency, year);

        //then

    }
}
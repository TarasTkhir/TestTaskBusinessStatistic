package com.testtask.demo.service;

import com.testtask.demo.appunits.AllCurrenciesUnit;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.*;

public class FixerAPICallingServiceImpTest {

    FixerAPICallingService service = new FixerAPICallingServiceImp();


    @Test
    public void givenAPIUrl_whenCreateData_thenReturnMapWithCurrencies_Pos() throws IOException {

        //given
        FileReader reader = new FileReader(System.getProperty("user.dir") + "\\src\\test\\resources\\application-integrationtest.properties");
        Properties p = new Properties();
        p.load(reader);
        String url = p.getProperty("external.test.url");

        //when
        Map<String, Float> data = service.createData(url);

        //then
        assertEquals(true, data.containsKey("USD"));
        assertEquals(true, data.containsKey("EUR"));
        assertEquals(true, data.containsKey("UAH"));
    }

    @Test(expected = RuntimeException.class)
    public void givenFakeAPIUrl_whenCreateData_thenReturnException_Neg() {

        //given
        String fakeUrl = "fakeUrl";

        //when
        Map<String, Float> data = service.createData(fakeUrl);

        //then
    }

    @Test
    public void givenAPIUrl_whenGetResponse_thenReturnAllCurrenciesUnitObjectWithResponse_Pos() throws IOException {

        //given
        FileReader reader = new FileReader(System.getProperty("user.dir") + "\\src\\test\\resources\\application-integrationtest.properties");
        Properties p = new Properties();
        p.load(reader);
        String url = p.getProperty("external.test.url");

        //when
        AllCurrenciesUnit response = service.getResponse(url);

        //then
        assertEquals("true", response.getSuccess());
        assertEquals("EUR", response.getBase());
        assertEquals(false, response.getRates().isEmpty());
    }
}
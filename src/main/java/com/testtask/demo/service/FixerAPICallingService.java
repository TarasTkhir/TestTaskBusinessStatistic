package com.testtask.demo.service;

import com.testtask.demo.appunits.AllCurrenciesUnit;
import com.testtask.demo.wire.DeletedWire;

import java.util.Map;

public interface FixerAPICallingService {

    AllCurrenciesUnit getResponse(String url);

    Map<String, Float> createData(String url);

}

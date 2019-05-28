package com.testtask.demo.utils;

import com.testtask.demo.domain.Purchase;
import com.testtask.demo.wire.FindAllWire;
import com.testtask.demo.wire.ReportWire;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

public class UtilsMethodsForPurchase {

    public static FindAllWire generatePagination(String host,
                                                 Pageable pageable, List resultList, String mapping) {

        FindAllWire paginationWire = new FindAllWire();
        paginationWire.setNext(host + mapping + pageable.next().getPageNumber());
        paginationWire.setPrevious(host + mapping + pageable.previousOrFirst().getPageNumber());
        paginationWire.setStartPage(host + mapping + pageable.first().getPageNumber());
        paginationWire.setResults(resultList);

        if (resultList.size() < pageable.next().getPageSize()) {
            paginationWire.setNext("N_O____R_E_S_U_L_T_S!");
            return paginationWire;
        }
        return paginationWire;
    }

    public static ReportWire calculateCurrencyForReport(Map<String, Float> allCurrency,
                                                        List<Purchase> allPurchasesByYear,
                                                        String currencyToConvert, Integer year) {

        Double total = allPurchasesByYear.stream()
                .mapToDouble(purchase ->
                        (purchase.getPrice()
                                / allCurrency.get(purchase.getCurrency()))
                                * allCurrency.get(currencyToConvert))
                .sum();

        return new ReportWire(year, currencyToConvert, new BigDecimal(total.toString())
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue());
    }
}

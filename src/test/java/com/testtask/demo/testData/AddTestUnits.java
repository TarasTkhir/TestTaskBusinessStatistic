package com.testtask.demo.testData;

import com.testtask.demo.domain.Purchase;
import com.testtask.demo.repository.PurchaseRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddTestUnits {

    public static Map<String, Float> generateTestMapOfFewMappedCurrencies() {

        Map<String, Float> testMap = new HashMap<>();
        testMap.put("USD", new Float(2));
        testMap.put("UAH", new Float(3));
        return testMap;
    }

    public static List<Purchase> generateListOfTestPurchases() {

        List<Purchase> testList = new ArrayList<>();

        Purchase purchase = new Purchase(1,
                LocalDate.of(2019, 8, 8), 20.00, "USD", "GoldPen");
        Purchase purchase1 = new Purchase(2,
                LocalDate.of(2019, 8, 8), 30.00, "UAH", "Car");

        testList.add(purchase);
        testList.add(purchase1);

        return testList;
    }


    public static void generateTestPurchases(PurchaseRepository repository) {

        repository.save(new Purchase(1,
                LocalDate.of(2019, 8, 8), 15.43, "USD", "GoldPen"));
        repository.save(new Purchase(2,
                LocalDate.of(2019, 8, 8), 16.43, "EUR", "beer"));
        repository.save(new Purchase(3,
                LocalDate.of(2016, 9, 8), 17.43, "BDT", "Pents"));
        repository.save(new Purchase(4,
                LocalDate.of(2019, 9, 9), 18.43, "BND", "GoldPen"));
        repository.save(new Purchase(5,
                LocalDate.of(2019, 9, 9), 19.43, "BZD", "Pensil"));
        repository.save(new Purchase(6,
                LocalDate.of(2019, 9, 9), 20.43, "CRC", "Computer"));
        repository.save(new Purchase(7,
                LocalDate.of(2019, 8, 8), 21.43, "CRC", "GoldPen"));
        repository.save(new Purchase(8,
                LocalDate.of(2019, 8, 8), 22.43, "USD", "Notepad"));
        repository.save(new Purchase(9,
                LocalDate.of(2019, 5, 9), 23.43, "USD", "GoldPen"));
        repository.save(new Purchase(10,
                LocalDate.of(2018, 12, 10), 24.43, "EUR", "Phone"));

    }
}

package com.testtask.demo.service;

import com.testtask.demo.appunits.CurrencyMapUnit;
import com.testtask.demo.domain.Purchase;
import com.testtask.demo.wire.DeletedWire;
import com.testtask.demo.wire.FindAllWire;
import com.testtask.demo.wire.InstructionWire;
import com.testtask.demo.wire.ReportWire;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PurchaseService {

    List<Purchase> addPurchase(String date, String priceToConvert, String currency, String productName);

    FindAllWire findAllSortedByDate(Pageable pageable);

    DeletedWire cleanAllByDate(String date);

    ReportWire ReportByEar(Integer year, String currency);

    InstructionWire getInstruction();

    void setAllСurrencies(CurrencyMapUnit currencyMapUnit);

    DeletedWire deleteAll();
}

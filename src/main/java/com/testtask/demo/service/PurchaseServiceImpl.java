package com.testtask.demo.service;

import com.testtask.demo.appunits.CurrencyMapUnit;
import com.testtask.demo.domain.Purchase;
import com.testtask.demo.exception.NoPurchaseInDatabaseException;
import com.testtask.demo.exception.WrongDataException;
import com.testtask.demo.repository.PurchaseRepository;
import com.testtask.demo.utils.UtilsMethodsForApp;
import com.testtask.demo.utils.UtilsMethodsForPurchase;
import com.testtask.demo.wire.DeletedWire;
import com.testtask.demo.wire.FindAllWire;
import com.testtask.demo.wire.InstructionWire;
import com.testtask.demo.wire.ReportWire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private PurchaseRepository purchaseRepository;

    private CurrencyMapUnit allСurrencies;

    @Value("${app.host}")
    private String host;

    @Value("${pagination.allResults.mapping}")
    private String paginationAllResultsMapping;

    @Value("${instruction.path}")
    private String instructionPath;

    @Autowired
    public void setPurchaseRepository(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @Autowired
    public void setAllСurrencies(CurrencyMapUnit allСurrencies) {
        this.allСurrencies = allСurrencies;
    }


    public String getHost() {
        return host;
    }

    public String getPaginationAllResultsMapping() {
        return paginationAllResultsMapping;
    }

    @Override
    public List<Purchase> addPurchase(String date, String priceToConvert, String currency, String productName) {

        double price;
        try {
            price = Double.parseDouble(priceToConvert);
        } catch (Exception e) {

            throw new WrongDataException("Invalid price");
        }

        if (!(allСurrencies.getAllCurrency().containsKey(currency.toUpperCase()))) {

            throw new WrongDataException("Currency not exist, input valid currency.");
        }
        try {
            if (LocalDate.parse(date).getYear() > LocalDate.now().getYear()) {

                throw new WrongDataException("Year must be equals or less than current year");
            }

            purchaseRepository.save(new Purchase(LocalDate.parse(date), price, currency.toUpperCase(), productName));
        } catch (DateTimeException ex) {

            throw new WrongDataException("Wrong format of date or invalid date. Must be: YYYY-MM-DD (Example: 2015-03-25)");
        }
        return purchaseRepository.findAllByDate(LocalDate.parse(date));
    }

    @Override
    public FindAllWire findAllSortedByDate(Pageable pageable) {

        List<Purchase> allByOrderByDateAsc = purchaseRepository.findAllByOrderByDateAsc(pageable);
        return UtilsMethodsForPurchase.generatePagination(this.host, pageable, allByOrderByDateAsc,
                this.paginationAllResultsMapping);
    }

    @Override
    @Transactional
    public DeletedWire cleanAllByDate(String date) {

        List<Purchase> allByDate;
        LocalDate parse;

        try {
            parse = LocalDate.parse(date);

            allByDate = purchaseRepository.findAllByDate(parse);

            if (allByDate.isEmpty()) {
                throw new NoPurchaseInDatabaseException("No purchases was found to delete by date: " + date);
            }
            purchaseRepository.deletePurchasesByDateIs(parse);

        } catch (DateTimeException ex) {

            throw new WrongDataException("Wrong format of date or invalid date. Must be: YYYY-MM-DD (Example: 2015-03-25)");
        }
        return new DeletedWire("D_E_L_E_T_E_D", parse, allByDate);
    }

    @Override
    public ReportWire ReportByEar(Integer year, String currency) {

        if (year > LocalDate.now().getYear()) {

            throw new WrongDataException("Year must be equals or less than current year");
        }

        String currencyValidate = currency.toUpperCase();

        Map<String, Float> allCurrency = allСurrencies.getAllCurrency();

        if (!(allCurrency.containsKey(currencyValidate))) {

            throw new WrongDataException("Not valid currency! Enter valid one.");

        }

        List<Purchase> byYear = purchaseRepository.findAllByDateYear(year);

        if (byYear.isEmpty()) {

            throw new NoPurchaseInDatabaseException("No purchases was found for this year.");
        }
        return UtilsMethodsForPurchase.calculateCurrencyForReport(allCurrency, byYear, currencyValidate, year);


    }

    @Override
    public DeletedWire deleteAll() {

        purchaseRepository.deleteAll();

        List<Purchase> list = new ArrayList<>();

        purchaseRepository.findAll().forEach(list::add);
        if (list.isEmpty()) {

            return new DeletedWire("All records D_E_L_E_T_E_D", LocalDate.now(), new ArrayList<>());
        }

        throw new NoPurchaseInDatabaseException("Cannot delete all records, try again!");
    }

    @Override
    public InstructionWire getInstruction() {

        List<String> instruction = UtilsMethodsForApp.getInstruction(this.instructionPath);

        return new InstructionWire(instruction, allСurrencies.getAllCurrency());
    }
}

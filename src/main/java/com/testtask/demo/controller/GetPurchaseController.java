package com.testtask.demo.controller;


import com.testtask.demo.appunits.CurrencyMapUnit;
import com.testtask.demo.service.PurchaseService;
import com.testtask.demo.utils.UtilsMethodsForApp;
import com.testtask.demo.wire.FindAllWire;
import com.testtask.demo.wire.InstructionWire;
import com.testtask.demo.wire.ReportWire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;
import java.time.LocalDate;


@RestController
@Validated
public class GetPurchaseController {

    private PurchaseService purchaseService;

    private int year = LocalDate.now().getYear();

    @Autowired
    public void setPurchaseService(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }


    @GetMapping("/")
    public ResponseEntity<InstructionWire> getInfoForStart() {

        return ResponseEntity.accepted().body(purchaseService.getInstruction());
    }

    @GetMapping("/all")
    public ResponseEntity<FindAllWire> findAllByOrderByDate(@PageableDefault(value = 5)
                                                                    Pageable pageable) {

        return ResponseEntity.accepted().body(purchaseService.findAllSortedByDate(pageable));
    }

    @GetMapping("/report/{year}/{currency}")
    public ResponseEntity<ReportWire> reportByYear(@PathVariable int year,
                                                   @PathVariable @Size(min = 3, max = 3,
                                                           message = "currency must contain 3 symbols") String currency) {

        return ResponseEntity.accepted().body(purchaseService.ReportByEar(year, currency));
    }

}

package com.testtask.demo.controller;


import com.testtask.demo.domain.Purchase;
import com.testtask.demo.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;
import java.util.List;

@RestController
@Validated
public class PostPurchaseController {

    private PurchaseService purchaseService;

    @Autowired
    public void setPurchaseService(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/purchase/{date}/{price}/{currency}/{productName}")
    public ResponseEntity<List<Purchase>> addPersonage(@PathVariable @Size(max = 10, message = "Too long or too short date!") String date,
                                                       @PathVariable String price,
                                                       @PathVariable @Size(max = 3, min = 3, message = "Currency must contain 3 letters") String currency,
                                                       @PathVariable @Size(max = 250, message = "Too long product name") String productName) {

        return ResponseEntity.accepted().body(purchaseService.addPurchase(date, price, currency, productName));
    }
}

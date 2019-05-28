package com.testtask.demo.controller;


import com.testtask.demo.service.PurchaseService;
import com.testtask.demo.wire.DeletedWire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;

@RestController
@Validated
public class DeletePurchaseController {

    private PurchaseService purchaseService;

    @Autowired
    public void setPurchaseService(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @DeleteMapping("/clear/{date}")
    public ResponseEntity<DeletedWire> deleteByDate(@PathVariable @Size(max = 10,
            message = "Not valid date") String date) {

        return ResponseEntity.accepted().body(purchaseService.cleanAllByDate(date));
    }

    @DeleteMapping("/clear/all")
    public ResponseEntity<DeletedWire> deleteAllPurchases() {

        return ResponseEntity.accepted().body(purchaseService.deleteAll());
    }
}

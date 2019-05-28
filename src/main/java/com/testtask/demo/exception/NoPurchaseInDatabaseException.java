package com.testtask.demo.exception;

public class NoPurchaseInDatabaseException extends MainBusinessException {

    public NoPurchaseInDatabaseException(String message) {
        super(message);
    }
}

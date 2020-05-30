/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.atm.exception;

public class ATMException extends RuntimeException {
    public ATMException(String message, Throwable cause) {
        super(message, cause);
    }
}

/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.atm.exception;

public class FailInitStateATMException extends ATMException {
    public FailInitStateATMException(String message, Throwable cause) {
        super(message, cause);
    }
}

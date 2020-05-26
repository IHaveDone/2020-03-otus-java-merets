/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.atm.exception;

public class NoMoneyATMException extends ATMException {
    public NoMoneyATMException(String message, Throwable cause) {
        super(message, cause);
    }
}

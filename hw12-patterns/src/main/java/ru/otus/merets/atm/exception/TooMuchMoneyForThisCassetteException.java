/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.atm.exception;

public class TooMuchMoneyForThisCassetteException extends ATMException {
    public TooMuchMoneyForThisCassetteException(String message, Throwable cause) {
        super(message, cause);
    }
}

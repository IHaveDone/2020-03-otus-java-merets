/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.atm.exception;

public class TooFewMoneyInBundleOfMoneyException extends ATMException {
    public TooFewMoneyInBundleOfMoneyException(String message, Throwable cause) {
        super(message, cause);
    }
}

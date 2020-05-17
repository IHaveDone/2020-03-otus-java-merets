/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.atm.exception;

public class TooFewMoneyInBundleOfMoneyException extends RuntimeException {
    public TooFewMoneyInBundleOfMoneyException(String message, Throwable cause) {
        super(message, cause);
    }
}

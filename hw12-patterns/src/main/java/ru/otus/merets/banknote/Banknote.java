/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.banknote;

/**
 * This is just a banknote, e.g. 100 RUR (only amount, without currency)
 */
public interface Banknote {
    /**
     * @return a denomination of the banknote
     */
    public int getDenomination();
}

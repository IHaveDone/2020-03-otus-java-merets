/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.banknote;

public enum Banknotes implements Banknote {
    BANKNOTE_100(100), BANKNOTE_200(200), BANKNOTE_500(500),
    BANKNOTE_1000(1000), BANKNOTE_2000(2000), BANKNOTE_5000(5000);
    private int denomination;

    Banknotes(int denomination) {
        this.denomination = denomination;
    }

    public int getDenomination() {
        return denomination;
    }
}

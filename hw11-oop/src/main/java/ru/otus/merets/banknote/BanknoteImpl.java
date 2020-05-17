/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.banknote;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class BanknoteImpl implements Banknote {
    private int denomination;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BanknoteImpl banknote = (BanknoteImpl) o;
        return denomination == banknote.denomination;
    }

    @Override
    public int hashCode() {
        return Objects.hash(denomination);
    }

    private BanknoteImpl(int denomination) {
            this.denomination = denomination;
    }

    @Override
    public int getDenomination() {
        return denomination;
    }


    public static final Banknote BANKNOTE_100 = new BanknoteImpl(100);
    public static final Banknote BANKNOTE_200 = new BanknoteImpl(200);
    public static final Banknote BANKNOTE_500 = new BanknoteImpl(500);
    public static final Banknote BANKNOTE_1000 = new BanknoteImpl(1000);
    public static final Banknote BANKNOTE_2000 = new BanknoteImpl(2000);
    public static final Banknote BANKNOTE_5000 = new BanknoteImpl(5000);
    public static final Banknote[] BANKNOTES_WHITELIST = {BANKNOTE_100, BANKNOTE_200, BANKNOTE_500, BANKNOTE_1000, BANKNOTE_2000, BANKNOTE_5000};

    public static boolean exists(Banknote banknote) {
        return Arrays.stream(BANKNOTES_WHITELIST).filter(b -> b == banknote).count() > 0 ? true : false;
    }

    public static boolean exists(int denomination) {
        return Arrays.stream(BANKNOTES_WHITELIST).filter(b -> b.getDenomination() == denomination).count() > 0 ? true : false;
    }
    public static Banknote getBanknoteByDenomination(int denomination) {
        if (exists(denomination)) {
            Optional<Banknote> banknote = Arrays.stream(BANKNOTES_WHITELIST).filter(b -> b.getDenomination() == denomination).findAny();
            return banknote.get();
        } else {
            return null;
        }
    }
}

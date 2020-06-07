/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.banknote;

import ru.otus.merets.atm.exception.TooFewMoneyInBundleOfMoneyException;

import java.util.*;

public class BundleOfMoneyImpl implements BundleOfMoney {
    private Map<Banknote, Integer> banknoteMap;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CashImpl{");
        banknoteMap.forEach((k, v) -> stringBuilder.append(k.getDenomination() + ":" + v + ","));
        stringBuilder.append(" balance:" + getBalance());
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void removeBanknote(Banknote banknote) {
        if (banknoteMap.containsKey(banknote)) {
            if (banknoteMap.get(banknote) > 0) {
                banknoteMap.put(banknote, banknoteMap.get(banknote) - 1);
            } else {
                throw new TooFewMoneyInBundleOfMoneyException("Impossible requirements to remove too many banknotes from this bundle of money", new Throwable());
            }
        }
    }

    @Override
    public void removeBanknotes(Banknote banknote, int number) {
        while (number > 0) {
            removeBanknote(banknote);
            number--;
        }
    }


    private BundleOfMoneyImpl(Map<Banknote, Integer> banknoteMap) {
        this.banknoteMap = banknoteMap;
    }

    public static class Builder {
        private Map<Banknote, Integer> banknoteMap;

        public Builder(Map<Banknote, Integer> banknoteMap) {
            if (banknoteMap == null) {
                this.banknoteMap = new HashMap<>();
            } else {
                this.banknoteMap = banknoteMap;
            }
        }

        public Builder withBanknotes(Banknote banknote, int num) {
            if (num > 0) {
                banknoteMap.put(banknote, num);
            }
            return this;
        }

        public BundleOfMoneyImpl build() {
            return new BundleOfMoneyImpl(banknoteMap);
        }
    }

    @Override
    public void addBanknote(Banknote banknote) {
        if (banknoteMap.containsKey(banknote)) {
            int num = banknoteMap.get(banknote) + 1;
            banknoteMap.put(banknote, num);
        } else {
            banknoteMap.put(banknote, 1);
        }
    }

    @Override
    public void addBanknotes(Banknote banknote, int num) {
        while (num > 0) {
            addBanknote(banknote);
            num--;
        }
    }

    @Override
    public Banknote[] getTypesOfBanknotes() {
        List<Banknote> banknotes = new ArrayList<>();
        for (Map.Entry<Banknote, Integer> banknoteEntry : banknoteMap.entrySet()) {
            banknotes.add(banknoteEntry.getKey());
        }
        Banknote[] usedBanknotes = {};
        usedBanknotes = banknotes.toArray(usedBanknotes);
        return usedBanknotes;
    }

    @Override
    public int getBalance() {
        return banknoteMap.entrySet().stream().mapToInt((b) -> b.getKey().getDenomination() * b.getValue()).sum();
    }

    @Override
    public int getNumberOfBanknotes(Banknote banknote) {
        if (banknoteMap.containsKey(banknote)) {
            return banknoteMap.get(banknote);
        }
        return 0;
    }

    @Override
    public boolean hasAnotherBanknotes(Banknote banknote) {
        return banknoteMap.entrySet().stream().filter(e -> e.getKey() != banknote).count() != 0;
    }
}

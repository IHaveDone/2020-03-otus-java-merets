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
            removeBanknotes(banknote, banknoteMap.get(banknote));
        }
    }

    @Override
    public void removeBanknotes(Banknote banknote, int number) {
        if (banknoteMap.containsKey(banknote)) {
            if (banknoteMap.get(banknote) > number) {
                banknoteMap.put(banknote, banknoteMap.get(banknote) - number);
            } else if (banknoteMap.get(banknote) == number) {
                banknoteMap.remove(banknote);
            } else {
                throw new TooFewMoneyInBundleOfMoneyException("Impossible requirements to remove too many banknotes from this bundle of money", new Throwable());
            }
        }
    }


    public BundleOfMoneyImpl() {
        banknoteMap = new HashMap<>();
    }

    @Override
    public void addBanknote(Banknote banknote) {
        addBanknotes(banknote, 1);
    }

    @Override
    public void addBanknotes(Banknote banknote, int num) {
        if (banknoteMap.containsKey(banknote)) {
            num += banknoteMap.get(banknote);
        }
        banknoteMap.put(banknote, num);
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
        return banknoteMap.entrySet().stream().filter(e -> e.getKey() != banknote).count() == 0 ? false : true;
    }
}

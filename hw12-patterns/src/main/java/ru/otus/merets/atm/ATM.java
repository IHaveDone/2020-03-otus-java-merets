/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.atm;

import ru.otus.merets.banknote.BundleOfMoney;

/**
 * ATM amulator
 */
public interface ATM {
    /**
     * ATM takes a bundle of money
     * @param bundleOfMoney
     */
    public void takeMoney( BundleOfMoney bundleOfMoney);

    /**
     * ATM gives a bundle of money equivalent amount
     * @param amount
     * @return
     */
    public BundleOfMoney giveMoney(int amount);

    /**
     * Returns a balance of the ATM (sum of banknotes of all cassettes)
     * @return
     */
    public int getBalance();

    /**
     * Let's call our employees who upload some cassets into the ATM
     * @param cassetteOne
     */
    public void addCassette(Cassette cassetteOne);
}

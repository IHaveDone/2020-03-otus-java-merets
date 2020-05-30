/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.atm;

import ru.otus.merets.banknote.Banknote;

/**
 * Case for an ATM
 */
public interface Cassette {
    public Cassette clone();
    /**
     * How many banknotes might be loaded into the cassette
     * @return
     */
    public int getCapacity();

    /**
     * How many banknotes loaded into the cassette currently
     * @return
     */
    public int getSize();

    /**
     * Get kind of banknotes from this cassette
     * @return
     */
    public Banknote getBanknote();

    /**
     * Add some money into the cassette
     * @param number
     */
    public void addBanknote(int number);

    /**
     * How many banknotes is possible to add
     * @return
     */
    public int getFreeSpace();

    /**
     * Remove number of banknotes from the cassette
     * @param number
     */
    public void removeBanknote(int number);

    /**
     * Test can I remove number of banknotes from the cassette or no
     * @param number
     * @return
     */
    public boolean canRemoveBanknote(int number);
}

/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.banknote;

/**
 * A bundle of banknotes. It can consists of two $100 banknotes or
 */
public interface BundleOfMoney {
    /**
     * @return amount of money in this bundle of money
     */
    public int getBalance();

    /**
     * @param banknote the banknote we wonder about
     * @return number of certain type of banknotes
     */
    public int getNumberOfBanknotes(Banknote banknote);

    /**
     * @param banknote the banknote we wonder about
     * @return number of banknotes which have another type, not like this banknote
     */
    public boolean hasAnotherBanknotes(Banknote banknote);

    /**
     * Add one more banktome to the bunlde
     *
     * @param banknote
     */
    public void addBanknote(Banknote banknote);

    /**
     * Add one more banktome to the bunlde
     *
     * @param banknote
     */
    public void addBanknotes(Banknote banknote, int num);

    /**
     * Which types of banknotes are here in the cash
     *
     * @return
     */
    public Banknote[] getTypesOfBanknotes();

    /**
     * Remove banknotes with banknote type from this cash
     *
     * @param banknote
     */
    public void removeBanknote(Banknote banknote);

    /**
     * Remove banknotes with banknote type from this cash
     *
     * @param banknote
     */
    public void removeBanknotes(Banknote banknote, int number);
}

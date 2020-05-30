/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.atm;

import ru.otus.merets.atm.exception.TooMuchMoneyForThisCassetteException;
import ru.otus.merets.banknote.Banknote;
import ru.otus.merets.banknote.BundleOfMoney;
import ru.otus.merets.banknote.BundleOfMoneyImpl;

public class CassetteImpl implements Cassette {
    private final Banknote typeOfBanknote;
    private BundleOfMoney bundleOfMoney;
    private int capacity;

    private CassetteImpl(Banknote typeOfbanknote, int capacity, int size) {
        this.typeOfBanknote = typeOfbanknote;
        this.capacity = capacity;
        bundleOfMoney = new BundleOfMoneyImpl.Builder(null).withBanknotes(this.typeOfBanknote,size).build();
    }
    public static class Builder{
        private Banknote typeOfBanknote;
        private int capacity;
        private int size;
        public Builder(Banknote typeOfBanknote){
            this.typeOfBanknote = typeOfBanknote;
            capacity = 200;
            size = 0;
        }

        public Builder withCapacity(int capacity){
            this.capacity = capacity;
            return this;
        }
        public Builder withSize(int size){
            this.size = size;
            return this;
        }
        public CassetteImpl build(){
            return new CassetteImpl(typeOfBanknote, capacity, size);
        }

    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getSize() {
        return bundleOfMoney.getNumberOfBanknotes(typeOfBanknote);
    }

    @Override
    public Banknote getBanknote() {
        return typeOfBanknote;
    }

    @Override
    public void addBanknote(int number) {
        if( bundleOfMoney.getNumberOfBanknotes(typeOfBanknote)+number <= getCapacity() ) {
            this.bundleOfMoney.addBanknotes(typeOfBanknote, number);
        } else {
            throw new TooMuchMoneyForThisCassetteException("You are trying to upload too many banknotes into this case", new Throwable());
        }
    }

    @Override
    public int getFreeSpace() {
        return capacity - bundleOfMoney.getNumberOfBanknotes(typeOfBanknote);
    }

    @Override
    public void removeBanknote(int number) {
        bundleOfMoney.removeBanknotes(typeOfBanknote,  number);
    }

    @Override
    public boolean canRemoveBanknote(int number) {
        return bundleOfMoney.getNumberOfBanknotes(typeOfBanknote)-number>=0?true:false;
    }
}

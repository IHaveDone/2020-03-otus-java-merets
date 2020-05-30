/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.atm;

import ru.otus.merets.atm.exception.IncorrectAmountException;
import ru.otus.merets.atm.exception.NoMoneyATMException;
import ru.otus.merets.atm.memento.Originator;
import ru.otus.merets.atm.memento.State;
import ru.otus.merets.banknote.Banknote;
import ru.otus.merets.banknote.BundleOfMoney;
import ru.otus.merets.banknote.BundleOfMoneyImpl;
import ru.otus.merets.department.command.Command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ATMImpl implements ATM {
    private List<Cassette> cassetteList;
    private Originator originator;

    private ATMImpl( List<Cassette> cassetteList ) {
        if(cassetteList==null) {
            this.cassetteList = new ArrayList<>();
        } else {
            this.cassetteList = cassetteList;
        }
        originator = new Originator();
        originator.saveState(new State(cassetteList));
    }

    public static class Builder{
        private final List<Cassette> cassetteList;
        public Builder(){
            cassetteList = new ArrayList<>();
        }
        public Builder withCassette( Cassette cassette ){
            cassetteList.add(cassette);
            return this;
        }
        public ATMImpl build(){
            return new ATMImpl(cassetteList);
        }
    }

    private List<Command> commands = new ArrayList<>();

    @Override
    public void rollback() {
        State state = originator.restoreInitState();
        cassetteList = state.getCassetteList();
    }

    @Override
    public void onCommand(Command command) {
        commands.add(command);
    }

    @Override
    public int execute() {
        int sum = commands.stream().mapToInt(cmd -> cmd.execute(this) ).sum();
        commands.clear();
        return sum;
    }

    @Override
    public void takeMoney(BundleOfMoney bundleOfMoney) {
        Banknote[] newBanknotes = bundleOfMoney.getTypesOfBanknotes();
        for (Banknote banknote : newBanknotes) {
            for (Cassette currentCassette : cassetteList) {
                if (currentCassette.getBanknote().equals(banknote)) {
                    currentCassette.addBanknote(bundleOfMoney.getNumberOfBanknotes(banknote));
                    bundleOfMoney.removeBanknote(banknote);
                }
            }
        }
        originator.saveState(new State(cassetteList));
    }

    private List<Banknote> getAllBanknoteTypes() {
        List<Banknote> allBanknoteTypesList = new ArrayList<>();

        for (Cassette currentCassette : cassetteList) {
            allBanknoteTypesList.add(currentCassette.getBanknote());
        }

        List<Banknote> allBanknoteTypesListNoDup = allBanknoteTypesList.stream().distinct().collect(Collectors.toList());
        Collections.sort(allBanknoteTypesListNoDup, (a,b) -> b.getDenomination()-a.getDenomination() );

        return allBanknoteTypesListNoDup;
    }

    @Override
    public BundleOfMoney giveMoney(int amount) {
        BundleOfMoney myBundleOfMoneyFromATM = new BundleOfMoneyImpl.Builder(null).build();
        if (getBalance() < amount) {
            throw new NoMoneyATMException(String.format("You asked too much money. ATM has %d, you asked %d", getBalance(), amount), new Throwable());
        }

        List<Banknote> banknoteTypes = getAllBanknoteTypes();

        for (Banknote currentBanknote : banknoteTypes) {
            if (amount >= currentBanknote.getDenomination()) {
                for (Cassette currentCassette : cassetteList) {
                    if (currentCassette.getBanknote().equals(currentBanknote)) {
                        while (currentCassette.canRemoveBanknote(1) && (amount - currentBanknote.getDenomination()) >= 0) {
                            currentCassette.removeBanknote(1);
                            amount -= currentBanknote.getDenomination();
                            myBundleOfMoneyFromATM.addBanknotes(currentBanknote, 1);
                        }
                    }
                }
            }
        }

        originator.saveState(new State(cassetteList));

        if (amount > 0) {
            takeMoney(myBundleOfMoneyFromATM);
            throw new IncorrectAmountException(String.format("ATM cannot give this amount of money (especially %d)", amount), new Throwable());
        }

        return myBundleOfMoneyFromATM;
    }

    @Override
    public int getBalance() {
        int balance = 0;
        for (Cassette currentCassette : cassetteList) {
            balance += currentCassette.getSize() * currentCassette.getBanknote().getDenomination();
        }
        return balance;
    }

    @Override
    public void addCassette(Cassette cassetteOne) {
        originator.saveState(new State(cassetteList));
        cassetteList.add(cassetteOne);
    }
}

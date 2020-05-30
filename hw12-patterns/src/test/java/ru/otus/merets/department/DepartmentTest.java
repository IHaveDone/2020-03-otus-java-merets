/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.department;

import org.junit.jupiter.api.BeforeEach;
import ru.otus.merets.atm.ATM;
import ru.otus.merets.atm.ATMImpl;
import ru.otus.merets.atm.CassetteImpl;
import ru.otus.merets.banknote.Banknotes;

public class DepartmentTest {
    private Department department;

    @BeforeEach
    public void init(){
        ATM atm1 = new ATMImpl.Builder()
                .withCassette( new CassetteImpl.Builder(Banknotes.BANKNOTE_5000).withSize(100).build() )
                .withCassette( new CassetteImpl.Builder(Banknotes.BANKNOTE_2000).withSize(100).build() )
                .withCassette( new CassetteImpl.Builder(Banknotes.BANKNOTE_1000).withSize(100).build() )
                .withCassette( new CassetteImpl.Builder(Banknotes.BANKNOTE_500).withSize(100).build() )
                .build();

        ATM atm2 = new ATMImpl.Builder()
                .withCassette( new CassetteImpl.Builder(Banknotes.BANKNOTE_500).withSize(100).build() )
                .withCassette( new CassetteImpl.Builder(Banknotes.BANKNOTE_200).withSize(100).build() )
                .withCassette( new CassetteImpl.Builder(Banknotes.BANKNOTE_1000).withSize(100).build() )
                .withCassette( new CassetteImpl.Builder(Banknotes.BANKNOTE_5000).withSize(100).build() )
                .build();

        ATM atm3 = new ATMImpl.Builder()
                .withCassette( new CassetteImpl.Builder(Banknotes.BANKNOTE_100).withSize(100).build() )
                .withCassette( new CassetteImpl.Builder(Banknotes.BANKNOTE_100).withSize(100).build() )
                .withCassette( new CassetteImpl.Builder(Banknotes.BANKNOTE_100).withSize(100).build() )
                .withCassette( new CassetteImpl.Builder(Banknotes.BANKNOTE_500).withSize(100).build() )
                .build();

        department = new Department();
        department.addATM(atm1);
        department.addATM(atm2);
        department.addATM(atm3);
    }


}

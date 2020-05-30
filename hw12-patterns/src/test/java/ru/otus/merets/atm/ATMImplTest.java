/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.atm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.merets.atm.exception.IncorrectAmountException;
import ru.otus.merets.atm.exception.NoMoneyATMException;
import ru.otus.merets.banknote.Banknotes;
import ru.otus.merets.banknote.BundleOfMoney;
import ru.otus.merets.banknote.BundleOfMoneyImpl;

@DisplayName("ATMImp must ")
public class ATMImplTest {
    private ATM atm;

    @BeforeEach
    public void init(){
        atm = new ATMImpl();
        Cassette cassetteOne100 = new CassetteImpl.Builder(Banknotes.BANKNOTE_100).withSize(10).build();
        Cassette cassetteOne200 = new CassetteImpl.Builder(Banknotes.BANKNOTE_200).withSize(10).build();
        Cassette cassetteOne500 = new CassetteImpl.Builder(Banknotes.BANKNOTE_500).withSize(10).build();
        Cassette cassetteOne1000 = new CassetteImpl.Builder(Banknotes.BANKNOTE_1000).withSize(10).build();
        atm.addCassette(cassetteOne100);
        atm.addCassette(cassetteOne200);
        atm.addCassette(cassetteOne500);
        atm.addCassette(cassetteOne1000);
    }

    @Test
    @DisplayName(" show correct balance")
    public void getBalanceTest(){
        Assertions.assertEquals( 1000*10+500*10+200*10+100*10, atm.getBalance() );
    }

    @DisplayName(" upload money via new case")
    @Test
    public void uploadMoneyTest(){
        Cassette cassette5000 = new CassetteImpl.Builder(Banknotes.BANKNOTE_5000).withSize(100).build();
        atm.addCassette(cassette5000);
        Assertions.assertEquals(1000*10+500*10+200*10+100*10+5000*100,atm.getBalance());
    }

    @Test
    @DisplayName(" take money")
    public void takeMoneyTest(){
        BundleOfMoney myBundleOfMoney = new BundleOfMoneyImpl.Builder(null)
                .withBanknotes(Banknotes.BANKNOTE_100,5)
                .withBanknotes(Banknotes.BANKNOTE_500,1)
                .build();
        atm.takeMoney(myBundleOfMoney);
        Assertions.assertEquals(1000*10+500*10+200*10+100*10 + 100*5+500, atm.getBalance());
    }

    @Test
    @DisplayName(" extract some money from the case")
    public void giveMoneyTest(){
        Cassette cassette5000 = new CassetteImpl.Builder(Banknotes.BANKNOTE_5000).withSize(100).build();
        atm.addCassette(cassette5000);

        BundleOfMoney myBundleOfMoneyFromATM = atm.giveMoney(100);
        Assertions.assertEquals(1000*10+500*10+200*10+100*10+200*5000 - 100, atm.getBalance());
        Assertions.assertEquals(100, myBundleOfMoneyFromATM.getBalance() );

        BundleOfMoney myBundleOfMoneyFromATM2 = atm.giveMoney(50900);

        Assertions.assertEquals(50900, myBundleOfMoneyFromATM2.getBalance() );
        Assertions.assertEquals(1000*10+500*10+200*10+100*10+200*5000 - 100 - 50900, atm.getBalance());
    }

    @Test
    @DisplayName(" throw NoMoneyATMException when you ask too much money")
    public void notToGiveMoneyTest(){
        Assertions.assertThrows(NoMoneyATMException.class, () -> atm.giveMoney(100000) );
    }

    @Test
    @DisplayName(" throw IncorrectAmountException when you ask strange amount ant ATM's balance shouldn't be changed")
    public void notToGiveStrangeAmount(){
        Assertions.assertEquals(1000*10+500*10+200*10+100*10, atm.getBalance());
        Assertions.assertThrows(IncorrectAmountException.class, ()->atm.giveMoney(501) );
        Assertions.assertEquals(1000*10+500*10+200*10+100*10, atm.getBalance());
    }
}

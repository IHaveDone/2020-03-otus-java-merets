/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.banknote;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CashImplTest must ")
public class BundleOfMoneyImplTest {
    private BundleOfMoney bundleOfMoney2x100;

    @BeforeEach
    public void init(){
        bundleOfMoney2x100 = new BundleOfMoneyImpl();
        bundleOfMoney2x100.addBanknotes( BanknoteImpl.BANKNOTE_100, 2 );
    }

    @Test
    @DisplayName(" return correct sum")
    public void getAmountTest(){
        Assertions.assertEquals(200, bundleOfMoney2x100.getBalance());
    }

    @Test
    @DisplayName(" return correct amount of certain banknotes")
    public void getNumberOfBanknotes(){
        Assertions.assertEquals(2, bundleOfMoney2x100.getNumberOfBanknotes( BanknoteImpl.BANKNOTE_100 ));
    }

    @Test
    @DisplayName(" add new banknotes correctly")
    public void addBanknoteTest(){
        BundleOfMoney bundleOfMoney2x100_1x1000 = new BundleOfMoneyImpl();
        bundleOfMoney2x100_1x1000.addBanknote( BanknoteImpl.BANKNOTE_100 );
        bundleOfMoney2x100_1x1000.addBanknote( BanknoteImpl.BANKNOTE_100 );
        bundleOfMoney2x100_1x1000.addBanknote( BanknoteImpl.BANKNOTE_1000 );
        Assertions.assertEquals( 1200, bundleOfMoney2x100_1x1000.getBalance() );
        Assertions.assertEquals( 1, bundleOfMoney2x100_1x1000.getNumberOfBanknotes( BanknoteImpl.BANKNOTE_1000 ) );
    }

    @DisplayName(" add a bulk of banknotes")
    @Test
    public void addBanknotesTest(){
        BundleOfMoney bundleOfMoney3x500_2x1000 = new BundleOfMoneyImpl();
        bundleOfMoney3x500_2x1000.addBanknotes(BanknoteImpl.BANKNOTE_500,3);
        bundleOfMoney3x500_2x1000.addBanknotes(BanknoteImpl.BANKNOTE_1000,2);
        Assertions.assertEquals(3500, bundleOfMoney3x500_2x1000.getBalance());
    }

}

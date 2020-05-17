/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.banknote;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BanknoteImpl must ")
public class BanknoteImplTest {

    @DisplayName(" return specific denomination")
    @Test
    public void getDenominationOfBanknotesTest() {
        Assertions.assertEquals(100, BanknoteImpl.BANKNOTE_100.getDenomination());
        Assertions.assertEquals(200, BanknoteImpl.BANKNOTE_200.getDenomination());
        Assertions.assertEquals(500, BanknoteImpl.BANKNOTE_500.getDenomination());
        Assertions.assertEquals(1000, BanknoteImpl.BANKNOTE_1000.getDenomination());
        Assertions.assertEquals(2000, BanknoteImpl.BANKNOTE_2000.getDenomination());
        Assertions.assertEquals(5000, BanknoteImpl.BANKNOTE_5000.getDenomination());
    }
}

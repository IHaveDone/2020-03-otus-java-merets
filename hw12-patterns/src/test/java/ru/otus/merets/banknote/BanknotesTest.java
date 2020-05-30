/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.banknote;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Banknotes must ")
public class BanknotesTest {

    @DisplayName(" return specific denomination")
    @Test
    public void getDenominationOfBanknotesTest() {
        Assertions.assertEquals(100, Banknotes.BANKNOTE_100.getDenomination());
        Assertions.assertEquals(200, Banknotes.BANKNOTE_200.getDenomination());
        Assertions.assertEquals(500, Banknotes.BANKNOTE_500.getDenomination());
        Assertions.assertEquals(1000, Banknotes.BANKNOTE_1000.getDenomination());
        Assertions.assertEquals(2000, Banknotes.BANKNOTE_2000.getDenomination());
        Assertions.assertEquals(5000, Banknotes.BANKNOTE_5000.getDenomination());
    }
}

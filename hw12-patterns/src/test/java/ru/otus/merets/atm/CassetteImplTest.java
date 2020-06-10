/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.atm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.merets.atm.exception.TooMuchMoneyForThisCassetteException;
import ru.otus.merets.banknote.Banknotes;

@DisplayName("CaseImpl must ")
public class CassetteImplTest {
    private CassetteImpl caseFor100;

    @BeforeEach
    public void init() {
        caseFor100 = new CassetteImpl.Builder(Banknotes.BANKNOTE_100).withSize(150).build();
    }

    @DisplayName(" return correct capacity")
    @Test
    public void getCapacityTest() {
        Assertions.assertEquals(200, caseFor100.getCapacity());
    }

    @DisplayName(" return correct number of uploaded banknotes")
    @Test
    public void getNumberOfBanknotes() {
        Assertions.assertEquals(150, caseFor100.getSize());
    }

    @DisplayName(" calc correct free space")
    @Test
    public void getFreeSpaceTest() {
        Assertions.assertEquals(50, caseFor100.getFreeSpace());
    }

    @DisplayName(" remove some banknotes from the case")
    @Test
    public void removeBanknoteTest() {
        caseFor100.removeBanknote(1);
        Assertions.assertEquals(149, caseFor100.getSize());
        caseFor100.removeBanknote(20);
        Assertions.assertEquals(129, caseFor100.getSize());
        caseFor100.removeBanknote(129);
        Assertions.assertEquals(0, caseFor100.getSize());
    }

    @Test
    @DisplayName(" not take too much money")
    public void notToAddBanknoteTest() {
        Cassette cassette = new CassetteImpl.Builder(Banknotes.BANKNOTE_5000).build();
        Assertions.assertThrows(TooMuchMoneyForThisCassetteException.class, () -> cassette.addBanknote(999999));
    }
}

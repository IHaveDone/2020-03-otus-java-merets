package ru.otus.merets.core.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User must ")
class UserTest {
    private final String USER_NAME = "Artem";
    private final int USER_AGE = 30;
    private final String USER_ADDRESS = "Komsomolskiy bulvar";
    private final String USER_PHONE = "70001110011";

    @Test
    @DisplayName(" link to the phone and reverse")
    public void testPhoneLink() {
        User user = new User.Builder(USER_NAME)
                .withAge(USER_AGE)
                .withAddress(USER_ADDRESS)
                .withPhone(USER_PHONE)
                .build();
        Assertions.assertEquals(user, user.getPhones().get(0).getUser());
    }

    @Test
    @DisplayName(" link to the address and reverse")
    public void testAddressLink() {
        User user = new User.Builder(USER_NAME)
                .withAge(USER_AGE)
                .withAddress(USER_ADDRESS)
                .withPhone(USER_PHONE)
                .build();
        Assertions.assertEquals(user, user.getAddress().getUser());
    }
}
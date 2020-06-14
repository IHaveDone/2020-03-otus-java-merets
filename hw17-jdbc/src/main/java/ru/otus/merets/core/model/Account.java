/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.core.model;

import ru.otus.merets.jdbc.mapper.Id;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {
    @Id
    private long no;
    private String type;
    private BigDecimal rest;

    public Account() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return no == account.no &&
                Objects.equals(type, account.type) &&
                Objects.equals(rest, account.rest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(no, type, rest);
    }

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", rest=" + rest +
                '}';
    }

    public Account(long no, String type, BigDecimal rest) {
        this.no = no;
        this.type = type;
        this.rest = rest;
    }
}

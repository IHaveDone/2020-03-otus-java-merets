package ru.otus.merets.core.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GenericGenerator(name = "accounts_sequence", strategy = "increment")
    @GeneratedValue(generator = "accounts_sequence")
    @Column(name = "account_id")
    private long id;

    @Column(name = "type")
    private String type;

    @Column(name = "rest")
    private int rest;

    public Account(String type, int rest) {
        this.id=0;
        this.type = type;
        this.rest = rest;
    }
    public Account(long id, String type, int rest) {
        this.id = id;
        this.type = type;
        this.rest = rest;
    }

    public Account() {
    }
}

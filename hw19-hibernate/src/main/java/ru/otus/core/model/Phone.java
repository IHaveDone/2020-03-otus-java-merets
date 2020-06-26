package ru.otus.core.model;

import javax.persistence.*;

@Entity
@Table(name="phones")
public class Phone {
    private String number;
    @Id
    @GeneratedValue
    private long phone_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;

    public Phone() {
    }

    public Phone(long phone_id, String number) {
        this.number = number;
        this.phone_id = phone_id;
    }
}

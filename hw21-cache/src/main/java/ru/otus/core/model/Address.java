package ru.otus.core.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {
    private String street;
    @Id
    @GenericGenerator(name="address_sequence" , strategy="increment")
    @GeneratedValue(generator="address_sequence")
    private long address_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;

    public Address(long address_id, String street) {
        this.street = street;
        this.address_id = address_id;
    }

    public Address() {
    }
}

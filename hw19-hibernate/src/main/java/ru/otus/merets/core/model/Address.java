package ru.otus.merets.core.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GenericGenerator(name = "address_generator", strategy = "increment")
    @GeneratedValue(generator = "address_generator")
    @Column(name="address_id")
    private long id;

    @Column(name = "street")
    private String street;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    public Address(String street) {
        this.id = 0;
        this.street = street;
    }

    public Address() {
    }

    public void setUser(User user){
        this.user = user;
    }
    public User getUser(){
        return this.user;
    }

    public String getStreet(){
        return this.street;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                '}';
    }
}

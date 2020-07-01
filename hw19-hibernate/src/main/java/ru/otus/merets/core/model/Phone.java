package ru.otus.merets.core.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "phones")
public class Phone {
    @Id
    @GenericGenerator(name = "phone_generator", strategy = "increment")
    @GeneratedValue(generator = "phone_generator")
    @Column(name = "phone_id")
    private long id;

    @ManyToOne()
    @JoinColumn(table = "phones", name="link")
    private User user;

    @Column(name = "number")
    private String number;

    public Phone(String number) {
        this.id = 0;
        this.number = number;
    }


    public Phone() {
    }

    public void setUser(User user) {
        this.user = user;
    }
    public User getUser(){
        return this.user;
    }

    public String getNumber(){
        return this.number;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}

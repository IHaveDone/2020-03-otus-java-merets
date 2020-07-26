package ru.otus.merets.core.model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GenericGenerator(name = "user_generator", strategy = "increment")
    @GeneratedValue(generator = "user_generator")
    @Column(name = "user_id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy="user",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address address;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Phone> phones;

    private User(long id, String name, int age, String password, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phones = phones;
        phones.stream().forEach(p -> p.setUser(this));
        this.address = address;
        if(this.address!=null) {
            this.address.setUser(this);
        }
        this.password = password;
    }

    private User() {
        this.phones = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }


    public void setAge(int age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public String getPassword(){
        return password;
    }

    @Override
    public String toString() {
        StringBuilder userInText = new StringBuilder();
        userInText.append("User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age);
        try {
            userInText.append(", address=" + address.toString());
        } catch (Exception e) {
        }
        try {
            userInText.append(", phones=" + phones.toString());
        } catch (Exception e) {
        }
        userInText.append("}");
        return userInText.toString();
    }

    public static class Builder {
        private long id;
        private String name;
        private int age;
        private Address address;
        private String password;
        private List<Phone> phones;

        public Builder(String name) {
            this.id = 0;
            this.name = name;
            this.age = 0;
            this.phones = new ArrayList<>();
            this.address = null;
            this.password = "password";
        }

        public Builder withAge(int age) {
            this.age = age;
            return this;
        }

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withPhone(String phone) {
            this.phones.add(new Phone(phone));
            return this;
        }

        public Builder withAddress(String street) {
            this.address = new Address(street);
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new User(this.id, this.name, this.age, this.password, this.address, this.phones);
        }
    }
}

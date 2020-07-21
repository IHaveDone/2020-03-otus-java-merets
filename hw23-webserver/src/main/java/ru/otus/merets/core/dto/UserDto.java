package ru.otus.merets.core.dto;

import org.hibernate.annotations.GenericGenerator;
import ru.otus.merets.core.model.Address;
import ru.otus.merets.core.model.Phone;
import ru.otus.merets.core.model.User;

import javax.persistence.*;
import java.util.List;

public class UserDto {
    private long id;
    private String name;
    private int age;
    public UserDto(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.age = user.getAge();
    }
}

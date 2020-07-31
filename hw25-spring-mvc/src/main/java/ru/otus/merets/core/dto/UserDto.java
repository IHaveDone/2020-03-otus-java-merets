package ru.otus.merets.core.dto;

import ru.otus.merets.core.model.User;

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

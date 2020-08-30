package ru.otus.merets.core.dto;

import ru.otus.merets.core.model.User;

public class UserDto {
    public long id;
    public String name;
    public int age;
    public UserDto(User user){
        if(user!=null) {
            this.id = user.getId();
            this.name = user.getName();
            this.age = user.getAge();
        }
    }
}

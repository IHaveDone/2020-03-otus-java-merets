package ru.otus.merets.controllers;

import org.springframework.web.bind.annotation.*;

import ru.otus.merets.core.model.User;
import ru.otus.merets.core.service.DBServiceUser;


@RestController
@RequestMapping("/api/user")
public class UserApiController {

    private final DBServiceUser userService;

    public UserApiController(DBServiceUser userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable(name = "id") long id ){
        return userService.getUser(id).get();
    }

    @PostMapping
    public void saveUser( @RequestBody User user ){
        userService.saveUser(user);
    }
}

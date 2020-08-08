package ru.otus.merets.controllers;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import ru.otus.merets.core.dto.UserDto;
import ru.otus.merets.core.model.User;
import ru.otus.merets.core.service.DBServiceUser;


@RestController
@RequestMapping("/api/user")
public class UserApiController {
    private static final Logger logger = LoggerFactory.getLogger(UserApiController.class);
    private final DBServiceUser userService;

    public UserApiController(DBServiceUser userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable(name = "id") long id ){
        return new UserDto(userService.getUser(id).get());
    }

    @PostMapping
    public UserDto saveUser( @RequestBody User user ){
        try {
            userService.saveUser(user);
            return new UserDto(user);
        } catch (Exception e){
            logger.error("Create user failed. ", e);
        }
        return null;
    }
}

package ru.otus.merets.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.merets.core.dto.UserDto;
import ru.otus.merets.core.model.User;
import ru.otus.merets.core.service.DBServiceUser;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class AdminController {

    private final DBServiceUser userService;

    public AdminController(DBServiceUser userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model) {
        List<User> users = userService.getAllUsers();
        List<UserDto> usersDto = users.stream().map(u -> new UserDto(u)).collect(Collectors.toList());
        model.addAttribute("users", usersDto);
        return "users.html";
    }
}

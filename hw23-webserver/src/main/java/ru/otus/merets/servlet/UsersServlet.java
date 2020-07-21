package ru.otus.merets.servlet;

import ru.otus.merets.core.dao.UserDao;
import ru.otus.merets.core.dto.UserDto;
import ru.otus.merets.core.model.User;
import ru.otus.merets.core.service.DBServiceUser;
import ru.otus.merets.server.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UsersServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "users.html";
    private static final String TEMPLATE_ATTR_RANDOM_USER = "randomUser";

    private final DBServiceUser dbServiceUser;
    private final TemplateProcessor templateProcessor;

    public UsersServlet(TemplateProcessor templateProcessor, DBServiceUser dbServiceUser) {
        this.templateProcessor = templateProcessor;
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        List<User> users = dbServiceUser.getAllUsers();
        List<UserDto> usersDto = new ArrayList<>();
        users.stream().forEach( p -> usersDto.add(new UserDto(p)));
        paramsMap.put( TEMPLATE_ATTR_RANDOM_USER, users);
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }

}

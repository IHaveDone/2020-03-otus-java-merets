package ru.otus.merets.servlet;

import com.google.gson.Gson;
import ru.otus.merets.core.dao.UserDao;
import ru.otus.merets.core.dto.UserDto;
import ru.otus.merets.core.model.User;
import ru.otus.merets.core.service.DBServiceUser;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Optional;


public class UsersApiServlet extends HttpServlet {

    private static final int ID_PATH_PARAM_POSITION = 1;

    private final DBServiceUser dbServiceUser;
    private final Gson gson;

    public UsersApiServlet(DBServiceUser dbServiceUser, Gson gson) {
        this.dbServiceUser = dbServiceUser;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Optional<User> optionalUser = dbServiceUser.getUser(extractIdFromRequest(request));

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();

        if (optionalUser.isPresent()) {
            out.print(gson.toJson(new UserDto(optionalUser.get())));
        } else {
            out.print("null");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        Integer age = Integer.valueOf(request.getParameter("age"));

        User newUser = new User.Builder(name)
                .withAge(age)
                .withPassword(password)
                .build();
        dbServiceUser.saveUser(newUser);

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(new UserDto(newUser)));
    }

    private long extractIdFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1) ? path[ID_PATH_PARAM_POSITION] : String.valueOf(-1);
        return Long.parseLong(id);
    }

}

package ru.otus.merets.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.merets.core.model.User;
import ru.otus.merets.core.service.DBServiceUser;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final DBServiceUser dbServiceUser;

    public UserRepositoryImpl(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;

        User artem = new User.Builder("Artem")
                .withAge(30)
                .withPhone("+70001112233")
                .withPhone("+70001112244")
                .withAddress("Varshavskoe highway")
                .withPassword("123456")
                .build();
        User elvira = new User.Builder("Elvira")
                .withAge(30)
                .withAddress("Varshavskoe highway")
                .withPassword("123456")
                .build();
        User dmitry = new User.Builder("Dmitry")
                .withAge(4)
                .withAddress("Varshavskoe highway")
                .withPassword("123456")
                .build();
        User maxim = new User.Builder("Maxim")
                .withAge(1)
                .withAddress("Varshavskoe highway")
                .withPassword("123456")
                .build();
        dbServiceUser.saveUser( artem );
        dbServiceUser.saveUser( elvira );
        dbServiceUser.saveUser( dmitry );
        dbServiceUser.saveUser( maxim );
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User save(User user) {
        dbServiceUser.saveUser(user);
        return user;
    }

    @Override
    public User findById(long id) {
        return dbServiceUser.getUser(id).orElse(null);
    }

    @Override
    public User findByName(String name) {
        //TODO: это не по имени
        return dbServiceUser.getUser(1).orElse(null);
    }
}

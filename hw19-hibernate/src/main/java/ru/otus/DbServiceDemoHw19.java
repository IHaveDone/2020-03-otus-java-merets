package ru.otus;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.Address;
import ru.otus.core.model.Phone;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.core.model.User;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DbServiceDemoHw19 {
    private static Logger logger = LoggerFactory.getLogger(DbServiceDemoHw19.class);

    public static void main(String[] args) {
        // Все главное см в тестах
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Address.class, Phone.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);


        List<Phone> phones = new ArrayList<>();
        phones.add( new Phone( 0, "123" ));

        long id = dbServiceUser.saveUser(new User(
                1,
                "Vasya",
                new Address(0, "NV"),
                phones));
        Optional<User> mayBeCreatedUser = dbServiceUser.getUser(id);

        id = dbServiceUser.saveUser(new User(
                1L,
                "This is not Vasya",
                new Address(1, "Chapaeva"),
                Arrays.asList(new Phone(0, "124"))));
        Optional<User> mayBeUpdatedUser = dbServiceUser.getUser(id);

        outputUserOptional("Created user", mayBeCreatedUser);
        outputUserOptional("Updated user", mayBeUpdatedUser);
    }

    private static void outputUserOptional(String header, Optional<User> mayBeUser) {
        System.out.println("-----------------------------------------------------------");
        System.out.println(header);
        mayBeUser.ifPresentOrElse(System.out::println, () -> logger.info("User not found"));
    }
}

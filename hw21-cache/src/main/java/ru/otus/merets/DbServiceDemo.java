package ru.otus.merets;

import java.sql.SQLException;
import java.util.Optional;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.merets.core.model.Address;
import ru.otus.merets.core.model.Phone;
import ru.otus.merets.core.service.DBServiceUser;
import ru.otus.merets.hibernate.HibernateUtils;
import ru.otus.merets.hibernate.dao.UserDaoHibernate;
import ru.otus.merets.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.merets.core.service.DbServiceUserImpl;
import ru.otus.merets.core.model.User;

public class DbServiceDemo {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) throws Exception {

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Phone.class, Address.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);

        var userDao = new UserDaoHibernate(sessionManager);
        var dbServiceUser = new DbServiceUserImpl(userDao);

        User artem = new User.Builder("Artem")
                .withAge(30)
                .withPhone("+70001112233")
                .withPhone("+70001112244")
                .withAddress("Varshavskoe highway")
                .build();

        var id = dbServiceUser.saveUser( artem );
        Optional<User> user = dbServiceUser.getUser(id);
        if(user.isPresent()) {
            user.get().getAddress();
        }
        user.ifPresent(p -> logger.info("User from DB: " + p.toString()));

    }
}

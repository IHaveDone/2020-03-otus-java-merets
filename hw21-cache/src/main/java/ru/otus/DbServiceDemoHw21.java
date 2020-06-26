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
import java.util.List;
import java.util.Optional;

public class DbServiceDemoHw21 {
    private static Logger logger = LoggerFactory.getLogger(DbServiceDemoHw21.class);

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Address.class, Phone.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        for(int i=1; i<500; i++) {
            List<Phone> phones = new ArrayList<>();
            phones.add( new Phone( i, "+7"+i ));

            dbServiceUser.saveUser(new User(
                    i,
                    "Vasya "+i,
                    new Address(i, "NV "+i),
                    phones));
        }

        logger.debug("Start many queries: ");
        long time = System.currentTimeMillis();
        //System.gc();
        for(int i=1; i<=300; i++){
            Optional<User> myInfinity = dbServiceUser.getUser( (Math.round( Math.random()%490))+1 );
            logger.trace("got "+i);
        }
        logger.debug("Stop many queries. "+(System.currentTimeMillis() - time));
    }


}

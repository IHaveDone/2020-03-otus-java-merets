package ru.otus.merets;

import java.util.Optional;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.merets.cachehw.HwListener;
import ru.otus.merets.cachehw.MyCache;
import ru.otus.merets.core.model.Address;
import ru.otus.merets.core.model.Phone;
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
        var myCache = new MyCache<String,User>();
        myCache.addListener(new HwListener<String, User>() {
            @Override
            public void notify(String key, User value, String action) {
                logger.info("Cash action ({}), object:{}", action, value);
            }
        });

        var dbServiceUser = new DbServiceUserImpl(userDao,myCache);

        for (int i = 1; i < 1000; i++) {
            User artem = new User.Builder("User#" + i)
                    .withAge(i)
                    .withPhone("+70001112" + i)
                    .withAddress("Varshavskoe highway, " + i)
                    .build();

            var id = dbServiceUser.saveUser(artem);
            Optional<User> user = dbServiceUser.getUser(id);
            if (user.isPresent()) {
                user.get().getAddress();
            }
            user.ifPresent(p -> logger.info("User from DB: " + p.toString()));
            logger.info("Cache info: " + dbServiceUser.getCacheInfo());
        }


        long startTimeCache = System.currentTimeMillis();
        for (int i = 1; i < 100; i++) {
            long randomNumber = Math.round(Math.random() * 1000);
            Optional<User> loadedUser = dbServiceUser.getUser(randomNumber);
            if(loadedUser.isPresent()) {
                long num = loadedUser.get().getId();
                logger.info("ID of this User is: {}, num:{}", num, randomNumber);
            }
        }
        long finishTimeCache = System.currentTimeMillis();

        logger.info("100 gets: {} ", finishTimeCache - startTimeCache);
    }
}

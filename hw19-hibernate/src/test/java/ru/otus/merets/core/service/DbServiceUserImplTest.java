package ru.otus.merets.core.service;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.merets.core.model.Address;
import ru.otus.merets.core.model.Phone;
import ru.otus.merets.core.model.User;
import ru.otus.merets.hibernate.HibernateUtils;
import ru.otus.merets.hibernate.dao.UserDaoHibernate;
import ru.otus.merets.hibernate.sessionmanager.SessionManagerHibernate;
import java.util.Optional;


@DisplayName("DbServiceUser must ")
class DbServiceUserImplTest {
    private SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Phone.class, Address.class);
    private SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
    private final String USER1_NAME = "Artem";
    private final int USER1_AGE = 30;
    private final String USER1_PHONE1 = "79001112233";
    private final String USER1_PHONE2 = "79990002233";
    private final String USER1_ADDRESS = "Varshavskoe highway";

    @Test
    @DisplayName(" save and load simple user from DB")
    public void saveAndLoadUser(){
        var userDao = new UserDaoHibernate(sessionManager);
        var dbServiceUser = new DbServiceUserImpl(userDao);
        var id = dbServiceUser.saveUser(
                new User.Builder(USER1_NAME)
                        .withAge(USER1_AGE)
                        .build()
        );
        Optional<User> user = dbServiceUser.getUser(id);
        User userFromDB = user.get();
        Assertions.assertEquals( USER1_NAME, userFromDB.getName() );
        Assertions.assertEquals( USER1_AGE, userFromDB.getAge() );
        Assertions.assertTrue( userFromDB.getId()>0 );
    }

    @Test
    @DisplayName(" save and load user with phone")
    public void saveAndLoadUserWithPhone(){
        var userDao = new UserDaoHibernate(sessionManager);
        var dbServiceUser = new DbServiceUserImpl(userDao);
        var id = dbServiceUser.saveUser(
                new User.Builder(USER1_NAME)
                        .withAge(USER1_AGE)
                        .withPhone(USER1_PHONE1)
                        .build()
        );
        Optional<User> user = dbServiceUser.getUser(id);
        User userFromDB = user.get();
        Assertions.assertEquals( USER1_PHONE1, userFromDB.getPhones().get(0).getNumber() );
    }

    @Test
    @DisplayName(" save and load user with two phones")
    public void saveAndLoadUserWithTwoPhones(){
        var userDao = new UserDaoHibernate(sessionManager);
        var dbServiceUser = new DbServiceUserImpl(userDao);
        var id = dbServiceUser.saveUser(
                new User.Builder(USER1_NAME)
                        .withAge(USER1_AGE)
                        .withPhone(USER1_PHONE1)
                        .withPhone(USER1_PHONE2)
                        .build()
        );
        Optional<User> user = dbServiceUser.getUser(id);
        User userFromDB = user.get();
        Assertions.assertEquals( 2, userFromDB.getPhones().size() );
        Assertions.assertEquals( USER1_PHONE1, userFromDB.getPhones().get(0).getNumber() );
        Assertions.assertEquals( USER1_PHONE2, userFromDB.getPhones().get(1).getNumber() );
    }

    @Test
    @DisplayName(" save and load user with address")
    public void saveAndLoadUserWithAddress(){
        var userDao = new UserDaoHibernate(sessionManager);
        var dbServiceUser = new DbServiceUserImpl(userDao);
        var id = dbServiceUser.saveUser(
                new User.Builder(USER1_NAME)
                        .withAge(USER1_AGE)
                        .withAddress(USER1_ADDRESS)
                        .build()
        );
        Optional<User> user = dbServiceUser.getUser(id);
        User userFromDB = user.get();
        Assertions.assertEquals( USER1_ADDRESS, userFromDB.getAddress().getStreet() );
    }

}
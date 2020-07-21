package ru.otus.merets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.SessionFactory;
import ru.otus.merets.core.dao.UserDao;
import ru.otus.merets.core.model.Address;
import ru.otus.merets.core.model.Phone;
import ru.otus.merets.core.model.User;
import ru.otus.merets.core.service.DbServiceUserImpl;
import ru.otus.merets.hibernate.HibernateUtils;
import ru.otus.merets.hibernate.dao.UserDaoHibernate;
import ru.otus.merets.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.merets.server.TemplateProcessor;
import ru.otus.merets.server.TemplateProcessorImpl;
import ru.otus.merets.server.UsersWebServer;
import ru.otus.merets.service.UserAuthService;
import ru.otus.merets.service.UserAuthServiceImpl;
import ru.otus.merets.server.UsersWebServerWithFilterBasedSecurity;

public class WebServerWithFilterBasedSecurityDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Phone.class, Address.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);

        var dbServiceUser = new DbServiceUserImpl(userDao, null);
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

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(dbServiceUser);

        UsersWebServer usersWebServer = new UsersWebServerWithFilterBasedSecurity(WEB_SERVER_PORT,
                authService, dbServiceUser, gson, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}

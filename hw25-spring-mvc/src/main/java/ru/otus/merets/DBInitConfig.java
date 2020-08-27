package ru.otus.merets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.merets.core.model.User;
import ru.otus.merets.core.service.DBServiceUser;

@Configuration
public class DBInitConfig {
    private final DBServiceUser dbServiceUser;

    public DBInitConfig(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Bean(initMethod = "initData")
    public DBInitConfig getConfig(){
        return new DBInitConfig(dbServiceUser);
    }

    public void initData(){
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
}

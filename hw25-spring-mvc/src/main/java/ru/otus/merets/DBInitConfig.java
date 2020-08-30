package ru.otus.merets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.merets.core.service.DBServiceUser;
import ru.otus.merets.services.DBInitService;
import ru.otus.merets.services.DBInitServiceImpl;

@Configuration
public class DBInitConfig {
    private final DBServiceUser dbServiceUser;

    public DBInitConfig(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Bean(initMethod = "initStorageWithData")
    public DBInitService dbInitService(){
        return new DBInitServiceImpl(dbServiceUser);
    }


}

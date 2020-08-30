package ru.otus.merets;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.merets.core.model.Address;
import ru.otus.merets.core.model.Phone;
import ru.otus.merets.core.model.User;
import ru.otus.merets.hibernate.HibernateUtils;

@Configuration
public class HibernateConfig {

    @Bean
    public SessionFactory createSessionFactory(){
        Class[] annotatedClasses = {User.class, Phone.class, Address.class};
        return HibernateUtils.buildSessionFactory("hibernate.cfg.xml", annotatedClasses);
    }
}

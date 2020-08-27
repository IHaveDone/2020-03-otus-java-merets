package ru.otus.merets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.merets.cachehw.MyCache;
import ru.otus.merets.core.model.User;

@Configuration
public class CacheConfig {
    @Bean
    public MyCache<String, User> myCache(){
        MyCache<String,User> myCache = new MyCache<>();
        myCache.addListener((key, value, action) -> System.out.println(action+" - "+key+":"+value));
        return myCache;
    }
}

package ru.otus.merets.webapp.test;

import ru.otus.merets.annotation.After;
import ru.otus.merets.annotation.Before;
import ru.otus.merets.annotation.Test;
import ru.otus.merets.webapp.WebApplication;

public class WebApplicationTest {
    private String className;
    private WebApplication webapp;
    public WebApplicationTest(){
//        this.className = className;
    }

    @Before
    public void init(){
        webapp = new WebApplication("10.10.10.10", "serviceName");
    }

    @Test
    public void runApplicationTest() throws Exception {
//remove comment from the line below to make success test-pass
// webapp.runApplication(); // <-- this one
        if(webapp.isRunning()!=true){
            throw new Exception("isRunning must be true");
        }
    }

    @Test
    public void getIPTest() throws Exception {
        if(!webapp.getIP().equals("10.10.10.10")){
            throw new Exception("getIP must return current address");
        }
    }

    @After
    public void close(){
    }
}

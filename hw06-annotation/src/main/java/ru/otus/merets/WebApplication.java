package ru.otus.merets;

public class WebApplication {
    private String ip;
    private String name;
    private boolean isRunning;

    public boolean isRunning() {
        return isRunning;
    }

    public void runApplication(){
        isRunning=true;
    }

    public void stopApplication(){
        isRunning=false;
    }

    public WebApplication(String ip, String name) {
        this.ip = ip;
        this.name = name;
    }

    @Override
    public String toString() {
        return "WebApplication{" +
                "ip='" + ip + '\'' +
                ", name='" + name + '\'' +
                ", isRunning=" + isRunning +
                '}';
    }

    public String getIP(){
        return ip;
    }

    public String getName(){
        return name;
    }
}

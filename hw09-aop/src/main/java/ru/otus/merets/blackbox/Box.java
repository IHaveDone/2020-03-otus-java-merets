package ru.otus.merets.blackbox;

public interface Box {
    public boolean getStatus(int actionCode );
    public boolean getStatus( );
    public void startAction( int actionCode, String actionParam );
    public void stopAction( int actionCode );

}

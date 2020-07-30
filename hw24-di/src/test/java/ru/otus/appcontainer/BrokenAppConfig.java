package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.services.IOService;
import ru.otus.services.PlayerService;
import ru.otus.services.PlayerServiceImpl;

@AppComponentsContainerConfig(order = 1)
public class BrokenAppConfig {
    @AppComponent(order = 1, name = "playerService")
    public PlayerService playerService(IOService ioService) {
        return new PlayerServiceImpl(ioService);
    }
}

/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.atm.memento;

import ru.otus.merets.atm.Cassette;

import java.util.ArrayList;
import java.util.List;

public class State {
    public State() {
        this.cassetteList = new ArrayList<>();
    }

    public State(List<Cassette> cassetteList) {
        this.cassetteList = cassetteList;
    }

    private List<Cassette> cassetteList;

    public List<Cassette> getCassetteList() {
        return cassetteList;
    }

    private void addCassette(Cassette cassette) {
        cassetteList.add(cassette);
    }


    public State clone() {
        State newState = new State();
        for (Cassette cassette : cassetteList) {
            newState.addCassette(cassette.clone());
        }
        return newState;
    }
}

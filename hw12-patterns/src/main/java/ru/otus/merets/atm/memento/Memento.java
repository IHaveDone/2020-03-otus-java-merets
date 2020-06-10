/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.atm.memento;

public class Memento {
    private final State state;

    public Memento(State state) {
        this.state = state.clone();
    }

    public State getState() {
        return state;
    }
}

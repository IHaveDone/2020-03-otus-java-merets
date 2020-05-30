/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.atm.memento;

import java.util.ArrayDeque;
import java.util.Deque;

public class Originator {
    private final Deque<Memento> stack = new ArrayDeque<>();

    public void saveState(State state) {
        stack.push(new Memento(state));
    }

    public State restoreState() {
        return stack.pop().getState();
    }

    public State restoreInitState() {
        State initState = stack.getLast().getState().clone();
        stack.clear();
        return initState;
    }
}

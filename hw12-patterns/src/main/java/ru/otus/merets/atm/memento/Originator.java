/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.atm.memento;

import ru.otus.merets.atm.exception.FailInitStateATMException;

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
        try {
            State initState = stack.getLast().getState().clone();
            stack.clear();
            return initState;
        } catch (Exception e){
            throw new FailInitStateATMException("Attempt to restore incorrect state", new Throwable());
        }
    }
}

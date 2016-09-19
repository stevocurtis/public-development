package com.fenixinfotech.fsm.statefulj;

import org.statefulj.persistence.annotations.State;

public class StatefulEntity {
    @State
    String state; // Memory Persister requires a String

    boolean isSet;

    public String getState() {
        return state;
    }

    public boolean isSet() {
        return isSet;
    }
}
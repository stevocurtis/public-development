package com.fenixinfotech.rxjava.playpen;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StandardStreams {

    public List<String> simpleMap(List<String> input) {
        if (input != null) {
            return input.stream().filter(i -> i != null).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

}
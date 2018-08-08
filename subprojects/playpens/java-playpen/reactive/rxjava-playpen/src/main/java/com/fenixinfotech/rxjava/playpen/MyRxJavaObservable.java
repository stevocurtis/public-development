package com.fenixinfotech.rxjava.playpen;

import io.reactivex.Observable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyRxJavaObservable {

    public Observable<Object> get(int numberEmissions) {
        List<String> emittedItems = new ArrayList<>();
        for (int i=0; i<numberEmissions; i++) {
            emittedItems.add(getTimestampedMessage());
        }
        return Observable.fromArray(emittedItems.toArray());
    }

    private String getTimestampedMessage() {
        return String.format("MyRxJavaObservable_%s", new SimpleDateFormat("YYYY-MM-DD_HH:MM:ss.SSS").format(new Date()));
    }

    // TODO flesh the following out
    public void getFLowableObservable(int max) {

    }

    public void getFutureCallableObservables(int max) {

    }
}
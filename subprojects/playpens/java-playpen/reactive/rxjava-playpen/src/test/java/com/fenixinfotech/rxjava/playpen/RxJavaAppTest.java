package com.fenixinfotech.rxjava.playpen;

import io.reactivex.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RxJavaAppTest {

    String result = "";

    @Test
    public void testReactiveSingleEmission() {
        String content = "Some Static Content";
        Observable<String> observer = Observable.just(content);
        observer.subscribe(s -> result = s);
        assertEquals(content, result);
    }

    @Test
    public void testReactiveMultipleEmissions() throws InterruptedException {
        int numberItemsToEmit = 50;
        MyRxJavaObserver observer = new MyRxJavaObserver();
        new MyRxJavaObservable().get(numberItemsToEmit).subscribeWith(observer);

        assertEquals(numberItemsToEmit, observer.getItems().size());
    }
}
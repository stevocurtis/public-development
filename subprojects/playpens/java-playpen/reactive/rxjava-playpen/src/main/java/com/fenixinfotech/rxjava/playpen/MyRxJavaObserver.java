package com.fenixinfotech.rxjava.playpen;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MyRxJavaObserver implements Observer {

    private static final Logger logger = LoggerFactory.getLogger(MyRxJavaObserver.class);

    private List items = null;

    public List getItems() {
        logger.debug("returning items {}", items);
        return items;
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        items = new ArrayList<>();
    }

    @Override
    public void onNext(Object o) {
        logger.debug("adding object {}", o);
        synchronized (this) {
            items.add(o);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        logger.error("error thrown", throwable);
    }

    @Override
    public void onComplete() {
        logger.debug("complete");
    }
}
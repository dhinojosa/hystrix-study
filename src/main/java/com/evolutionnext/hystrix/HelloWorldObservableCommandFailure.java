package com.evolutionnext.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class HelloWorldObservableCommandFailure extends HystrixObservableCommand<String> {
    private final String name;

    /**
     * Hystrix uses the command group key to group together commands
     * such as for reporting, alerting, dashboards, or team/library ownership.
     */
    public HelloWorldObservableCommandFailure(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    /**
     * Override the construct instead
     * @return Observable<String> of what we mean to say and what we mean!
     */
    @Override
    @SuppressWarnings("Duplicates")
    protected Observable<String> construct() {
        System.out.println("Running Hello World Observable Command!");
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> observer) {
                try {
                    if (!observer.isUnsubscribed()) {
                        // a real example would do work like a network call here
                        observer.onNext("Hello");
                        observer.onNext(name + "!");
                        throw new RuntimeException("Error Occurred");
                    }
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    protected Observable<String> resumeWithFallback() {
        return Observable.just("Error Hello " + name);
    }
}

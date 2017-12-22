package com.evolutionnext.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HelloWorldCommand extends HystrixCommand<String> {

    private final String name;

    public HelloWorldCommand(String name) {
        /**
         * Hystrix uses the command group key to group together commands
         * such as for reporting, alerting, dashboards, or team/library ownership.
         */
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() {
        // a real example would do work like a network call here
        System.out.println("Running Hello World Command!");
        System.out.println("Hysterix Thread:" + Thread.currentThread().getName());
        return "Hello " + name + "!";
    }

}

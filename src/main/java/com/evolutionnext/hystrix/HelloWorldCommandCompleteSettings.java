package com.evolutionnext.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

public class HelloWorldCommandCompleteSettings extends HystrixCommand<String> {

    private final String name;

    public HelloWorldCommandCompleteSettings(String name) {
        /**
         * Hystrix uses the command group key to group together commands
         * such as for reporting, alerting, dashboards, or team/library ownership.
         *
         * The thread-pool key represents a HystrixThreadPool for monitoring,
         *  metrics publishing, caching, and other such uses.
         *
         *
         */
       // super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"),
       //         HystrixThreadPoolKey.Factory.asKey("ExampleThreadPool"));

        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("CommandKey")));
        this.name = name;
    }

    @Override
    protected String run() {
        // a real example would do work like a network call here
        System.out.println("Running Hello World Command!");
        return "Hello " + name + "!";
    }

}

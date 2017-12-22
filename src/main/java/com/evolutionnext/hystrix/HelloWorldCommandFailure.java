package com.evolutionnext.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HelloWorldCommandFailure extends HystrixCommand<String> {

    private final String name;

    /**
     * Hystrix uses the command group key to group together commands
     * such as for reporting, alerting, dashboards, or team/library ownership.
     */
    public HelloWorldCommandFailure(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    /**
     * You can wrap the exception that you would like to throw
     * in HystrixBadRequestException and retrieve it via getCause().
     * The HystrixBadRequestException is intended for use cases such as reporting
     * illegal arguments or non-system failures that should not count against the failure
     * metrics and should not trigger fallback logic.
     */
    @Override
    protected String run() {
        throw new RuntimeException("this command always fails");
    }

    @Override
    protected String getFallback() {
        return "Hello Failure " + name + "!";
    }
}
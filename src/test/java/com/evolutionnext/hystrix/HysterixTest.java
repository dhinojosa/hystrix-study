package com.evolutionnext.hystrix;

import org.junit.Test;
import rx.Observable;

import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

public class HysterixTest {


    @Test
    public void testCommandExecute() {
        String result = new HelloWorldCommand("World").execute();
        assertThat(result).isEqualTo("Hello World!");
    }

    @Test
    public void testCommandQueue() throws Exception {
        Future<String> future = new HelloWorldCommand("World").queue();
        String result  = future.get(); //blocking
        assertThat(result).isEqualTo("Hello World!");
    }

    @Test
    public void testCommandToHotObservable() throws Exception {
        Observable<String> observable = new HelloWorldCommand("World").observe();
        Thread.sleep(1000);
        System.out.println("Subscribing now!");
        observable.subscribe(System.out::println);
    }

    @Test
    public void testCommandToColdObservable() throws Exception {
        Observable<String> observable = new HelloWorldCommand("World").toObservable();
        Thread.sleep(1000);
        System.out.println("Subscribing now!");
        observable.subscribe(System.out::println);
        Thread.sleep(1000);
    }

    @Test
    public void testObservableCommandToHotObservable() throws Exception {
        Observable<String> observable = new HelloWorldObservableCommand("World").observe();
        Thread.sleep(1000);
        System.out.println("Subscribing now!");
        observable.subscribe(System.out::println);
        Thread.sleep(1000);
    }

    @Test
    public void testObservableCommandToColdObservable() throws Exception {
        Observable<String> observable = new HelloWorldObservableCommand("World").toObservable();
        Thread.sleep(1000);
        System.out.println("Subscribing now!");
        observable.subscribe(System.out::println);
        Thread.sleep(1000);
    }

    @Test
    public void testCommandFailure() {
        assertThat(new HelloWorldCommandFailure("World").execute()).isEqualTo("Hello Failure World!");
        assertThat(new HelloWorldCommandFailure("Bob").execute()).isEqualTo("Hello Failure Bob!");
    }

    @Test
    public void testObservableCommandFailureAsHotObservable() throws InterruptedException {
        Observable<String> observable = new HelloWorldObservableCommandFailure("World").observe();
        Thread.sleep(1000);
        System.out.println("Subscribing now!");
        observable.subscribe(System.out::println);
        Thread.sleep(1000);
    }
}

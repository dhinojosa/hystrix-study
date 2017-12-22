package com.evolutionnext.hystrix;

import rx.Observable;
import org.junit.Test;

import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

public class HysterixTest {


    /**
     * execute() runs a command synchronously
     *
     * @throws Exception
     */
    @Test
    public void testCommandExecute() throws Exception {
        String result = new HelloWorldCommand("World").execute();
        assertThat(result).isEqualTo("Hello World!");
    }

    @Test
    public void testCommandQueue() throws Exception {
        Future<String> future = new HelloWorldCommand("World").queue();
        String result  = future.get(); //blocking
        assertThat(result).isEqualTo("Hello World!");
    }

    /**
     * Runs it hot!
     * @throws Exception
     */
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

    /**
     * The thing about Observable command failures is that they
     * will post items before the error since it is streaming
     * It uses Internally, the RxJava onErrorResumeNext
     */
    @Test
    public void testObservableCommandFailureAsHotObservable() throws InterruptedException {
        Observable<String> observable = new HelloWorldObservableCommandFailure("World").observe();
        Thread.sleep(1000);
        System.out.println("Subscribing now!");
        observable.subscribe(System.out::println);
        Thread.sleep(1000);
    }
}

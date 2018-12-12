package com.holkem.AsyncUsingRxJava;

public class Main {

    public static void main(String[] args) {

        // equivalent to Flow.Subscriber and Flow.Publisher
        // TempReadingObservable.getFahrenheitObservable("Somewhere")
        //         .subscribe(new TempReadingObserver());

        // equivalent to using processor in between Flow.Subscriber and Flow.Publisher
        // TempReadingObservable.getCelsiusObservable("Somewhere")
        //         .subscribe(new TempReadingObserver());

        TempReadingObservable.getCelsiusObservable("Location1", "Location2", "Location3")
                .subscribe(new TempReadingObserver());

        waitRxProcess(); // delays the exit of main in order to finish the emission of the observable since by default threads from RxJava thread pool are set to daemon
    }

    private static void waitRxProcess() {
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}

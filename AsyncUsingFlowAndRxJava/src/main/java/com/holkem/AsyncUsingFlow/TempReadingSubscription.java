package com.holkem.AsyncUsingFlow;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.*;

public class TempReadingSubscription implements Subscription {
    private final String town;
    private final Subscriber<? super TemperatureReading> subscriber; /* must have this in order to send the fetched data to and/or inform for completion or error */
    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    /* Error: incompatible types: java.util.concurrent.Flow.Subscriber
        <capture#1 of ? super com.holkem.TemperatureReading> cannot
        be converted to java.util.concurrent.Flow.Subscriber<com.holkem
        .TemperatureReading>
       Correction: added "? super " in the declaration of subscriber */

    public TempReadingSubscription(String town, Subscriber<? super TemperatureReading> subscriber) {
        this.town = town;
        this.subscriber = subscriber;
    }

    @Override
    public void request(long n) { // for backpressure, limits the number of request

        // do the execution in a separate thread with the static executor
        executor.submit(() -> {

            // for each request, must check for error and propagate it to subscriber
            for (int i = 0; i < n; i++) {
                try {
                    subscriber.onNext(TemperatureReading.getReading(town));
                } catch (Exception e) {
                    subscriber.onError(e);
                    break;
                }
            }
        });
    }

    @Override
    public void cancel() {
        // cancellation of a subscription means it is already complete
        subscriber.onComplete();
    }
}

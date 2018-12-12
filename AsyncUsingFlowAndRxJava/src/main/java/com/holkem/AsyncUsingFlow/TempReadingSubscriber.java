package com.holkem.AsyncUsingFlow;

import java.util.concurrent.Flow.*;

public class TempReadingSubscriber implements Subscriber<TemperatureReading> {
    private Subscription subscription; // to save the instance of subscription created by the publisher upon subscription

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1); // fetch first reading
    }

    @Override
    public void onNext(TemperatureReading item) {
        System.out.println(item); // prints the fetched reading
        subscription.request(1); // fetch new reading
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println(throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("Completed!");
    }
}

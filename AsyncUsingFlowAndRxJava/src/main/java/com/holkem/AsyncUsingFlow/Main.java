package com.holkem.AsyncUsingFlow;

import java.util.concurrent.Flow.Publisher;

public class Main {
    public static void main(String[] args) {
        getFahrenheitPublisher("Atlantis").subscribe(new TempReadingSubscriber());
        getCelsiusPublisher("Earth").subscribe(new TempReadingSubscriber());
    }

    private static Publisher<TemperatureReading> getFahrenheitPublisher(String town) {
        return subscriber -> subscriber.onSubscribe(new TempReadingSubscription(town, subscriber));
    }

    private static Publisher<TemperatureReading> getCelsiusPublisher(String town) {
        return subscriber -> {
            TempReadingProcessor processor = new TempReadingProcessor();
            processor.subscribe(subscriber); // make subscriber to subscribe to this processor instead of the main publisher
            processor.onSubscribe(new TempReadingSubscription(town, processor)); // make processor to subscribe to the subscription
        };
    }
}

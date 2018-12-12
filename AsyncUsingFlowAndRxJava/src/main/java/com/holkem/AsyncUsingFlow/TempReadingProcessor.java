package com.holkem.AsyncUsingFlow;

import java.util.concurrent.Flow.*;

public class TempReadingProcessor implements Processor<TemperatureReading, TemperatureReading> {
    // private Subscriber<? super TemperatureReading> subscriber;
    private Subscriber<? super TemperatureReading> subscriber;

    @Override
    public void subscribe(Subscriber<? super TemperatureReading> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void onNext(TemperatureReading item) {
        /* apply the processing of the fahrenheit reading to celsius,
            calling the same method from subscriber but with the new
            calculation */
        subscriber.onNext(new TemperatureReading(
                item.getTown(),
                ((item.getTemperature() - 32) * 5 / 9),
                TemperatureReading.TempUnit.Celsius)
        );
    }

    // will not be implemented, call the subscriber methods instead
    @Override
    public void onSubscribe(Subscription subscription) {
        subscriber.onSubscribe(subscription);
    }

    @Override
    public void onError(Throwable throwable) {
        subscriber.onError(throwable);
    }

    @Override
    public void onComplete() {
        subscriber.onComplete();
    }
}

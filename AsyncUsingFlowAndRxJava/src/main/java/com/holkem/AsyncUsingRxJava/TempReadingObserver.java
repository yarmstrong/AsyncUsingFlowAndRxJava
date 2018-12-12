package com.holkem.AsyncUsingRxJava;

import com.holkem.AsyncUsingFlow.TemperatureReading;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TempReadingObserver implements Observer<TemperatureReading> {
    @Override
    public void onSubscribe(Disposable disposable) {
        // no implementation
    }

    @Override
    public void onNext(TemperatureReading temperatureReading) {
        System.out.println(temperatureReading);
        // no backpressure like in Flow
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

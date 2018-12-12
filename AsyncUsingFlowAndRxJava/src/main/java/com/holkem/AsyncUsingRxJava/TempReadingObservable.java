package com.holkem.AsyncUsingRxJava;

import com.holkem.AsyncUsingFlow.TemperatureReading;
import io.reactivex.Observable;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TempReadingObservable {

    public static Observable<TemperatureReading> getFahrenheitObservable(String town) {
        return Observable.create(emitter
                -> Observable.interval(1, TimeUnit.SECONDS).subscribe(interval -> {
                    if (!emitter.isDisposed()) {
                        if (interval >= 5) { // limits to 5 emission only
                            emitter.onComplete();
                        } else {
                            try {
                                emitter.onNext(TemperatureReading.getReading(town));
                            } catch (Exception e) {
                                emitter.onError(e);
                            }
                        }
                    } // end if
                }) // end of lambda Observable.subscribe()
        );// end of lambda Observable.create()
    }

    // transforming the emission from getFahrenheitObservable from fahrenheit to celsius using the Observable.map()
    public static Observable<TemperatureReading> getCelsiusObservable(String town) {
        return getFahrenheitObservable(town)
                .map(temp -> new TemperatureReading(
                        temp.getTown(),
                        ((temp.getTemperature() - 32) * 5 / 9),
                        TemperatureReading.TempUnit.Celsius)); // logic of mapping fxn copied from com.holkem.AsyncUsingFlow.TempReadingProcessor
    }

    // overloading the getCelsiusObservable to get temp reading from 5 towns and merge emission into a single one using the Observable.merge()
    public static Observable<TemperatureReading> getCelsiusObservable(String... towns) {
        return Observable.merge(Arrays.stream(towns)
                .map(TempReadingObservable::getCelsiusObservable)
                .collect(Collectors.toList())
        );
    }
}

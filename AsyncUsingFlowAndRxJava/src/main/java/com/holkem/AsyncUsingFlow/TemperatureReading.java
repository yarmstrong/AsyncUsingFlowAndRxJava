package com.holkem.AsyncUsingFlow;

import java.util.Random;

public class TemperatureReading {
    private String town;
    private int temperature;
    private TempUnit tempUnit;
    private static Random random = new Random();

    public enum TempUnit {
        Fahrenheit("F"), Celsius("C");
        private String unit;
        TempUnit(String unit) {
            this.unit = unit;
        }
    }

    public TemperatureReading(String town, int temperature) {
        this(town, temperature, TempUnit.Fahrenheit);
    }

    public TemperatureReading(String town, int temperature, TempUnit tempUnit) {
        this.town = town;
        this.temperature = temperature;
        this.tempUnit = tempUnit;
    }

    public static TemperatureReading getReading(String town) {
        // simulate an error in every 10 reading
        if (random.nextInt(10) == 0) throw new RuntimeException("Something went wrong!");
        return new TemperatureReading(town, random.nextInt(75));
    }

    public String getTown() {
        return town;
    }

    public int getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return town + ":" + temperature + tempUnit.unit;
    }
}

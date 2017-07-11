package com.example.android.mymapapp;


class LocationWeather {

    private final double temperature;
    private final double humidity;

    LocationWeather(double temperature, double humidity){

        this.temperature = temperature;
        this.humidity = humidity;
    }

    double getTemperature() {
        return temperature;
    }

    double getHumidity() {
        return humidity;
    }
}

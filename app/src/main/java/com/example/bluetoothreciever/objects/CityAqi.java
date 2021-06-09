package com.example.bluetoothreciever.objects;

import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;
import java.util.StringTokenizer;

public  class CityAqi implements Serializable {

    public double aqi;
    public String city;
    String savedTime;

    public String getSavedTime() {
        return savedTime;
    }

    public CityAqi(double aqi, String city, String t) {
        this.aqi = aqi;
        this.city = city;
        this.savedTime = t;
    }

    public double getAqi() {
        return aqi;
    }

    public String getCity() {
        return city;
    }
}

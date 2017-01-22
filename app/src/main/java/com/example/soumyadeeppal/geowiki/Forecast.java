package com.example.soumyadeeppal.geowiki;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for holding weather forecast information for each day in 5 day forecast
 */
public class Forecast implements Serializable {
    public String date;
    public double temp_day;
    public double temp_min;
    public double temp_max;
    public double temp_night;
    public double temp_eve;
    public double temp_morn;

    public double pressure;
    public double humidity;

    public String weather_desc;
    public String icon_name;

    public double wind_speed;
    public double wind_degree;
    public double clouds;

    public void setTemp_night(double temp_night) {
        this.temp_night = temp_night-273.15;
    }

    public void setDate(long date) {
        Date d=new Date(date*1000);
        SimpleDateFormat formatter=new SimpleDateFormat("E,MMM,d");

        this.date = formatter.format(d).toString();
    }

    public void setTemp_day(double temp_day) {
        this.temp_day = temp_day-273.15;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min-273.15;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max-273.15;
    }

    public void setTemp_eve(double temp_eve) {
        this.temp_eve = temp_eve-273.15;
    }

    public void setTemp_morn(double temp_morn) {
        this.temp_morn = temp_morn-273.15;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setWeather_desc(String weather_desc) {
        this.weather_desc = weather_desc;
    }

    public void setIcon_name(String icon_name) {
        this.icon_name = icon_name;
    }

    public void setWind_speed(double wind_speed) {
        this.wind_speed = wind_speed;
    }

    public void setWind_degree(double wind_degree) {
        this.wind_degree = wind_degree;
    }

    public void setClouds(double clouds) {
        this.clouds = clouds;
    }

}

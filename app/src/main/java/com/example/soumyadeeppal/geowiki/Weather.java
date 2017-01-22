package com.example.soumyadeeppal.geowiki;

import android.graphics.Bitmap;
import android.location.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


/**
 *      Utility class to hold current weather data of search city
 */
public class Weather implements Serializable {

    public int weather_id;      // for weather id
    public String weather_icon;     // for weather icon
    public Bitmap weather_image;        // for weather image


    public String weather_description;


    public double avg_temp, min_temp, max_temp, humidity, pressure;     // weather information
    public double lat,lon;
    public double wind_speed, wind_degree;



    public double cloudiness;



    public String sunrise;
    public String sunset;
    public String date;

    public String city_name;        // location information
    public String country;



    public void setCloudiness(double cloudiness) {
        this.cloudiness = cloudiness;
    }


    public void setSunrise(long sunrise) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.setTimeInMillis(sunrise * 1000);


        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String dateString1 = sdf.format(calendar.getTime());
        this.sunrise=dateString1;
    }

    public void setSunset(long sunset) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.setTimeInMillis(sunset * 1000);


        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
        String dateString2 = sdf2.format(calendar.getTime());
        this.sunset=dateString2;                            // format obtained : e.g. 2009-06-21 15:51:25

    }

    public void setDate(long date) {
        Date d = new Date(date * 1000);
        SimpleDateFormat format = new SimpleDateFormat("E,MMM,d");
        this.date = format.format(d).toString();
    }



    public void setWeather_image(Bitmap weather_image) {
        this.weather_image = weather_image;
    }



    public String getWeather_icon() {
        return weather_icon;
    }





    public void setWeathericon(String weathericon) {
        this.weather_icon = weathericon;
    }

    public void setWeatherid(int weatherid) {
        this.weather_id = weatherid;
    }



    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }


    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public void setWeather_description(String weather_description) {
        this.weather_description = weather_description;
    }

    public void setAvg_temp(double avg_temp) {
        this.avg_temp = avg_temp-273.15;
    }

    public void setMin_temp(double min_temp) {
        this.min_temp = min_temp-273.15;
    }

    public void setMax_temp(double max_temp) {
        this.max_temp = max_temp-273.15;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setWind_speed(double wind_speed) {
        this.wind_speed = wind_speed;
    }

    public void setWind_degree(double wind_degree) {
        this.wind_degree = wind_degree;
    }

    public void setCountry(String country) {
        this.country = country;
    }




}

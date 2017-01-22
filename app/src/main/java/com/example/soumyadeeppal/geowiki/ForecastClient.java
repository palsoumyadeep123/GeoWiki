package com.example.soumyadeeppal.geowiki;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Utility class handling HTTP connection to openweathermaps.org API for retreiving weather data for 5 day forecast
 */
public class ForecastClient {

    HttpURLConnection connection=null;
    String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";

    InputStream is1=null;

    public String getForecastData(String city) {
        try {

            String forecast_url_string = FORECAST_BASE_URL + "q=" + city + "&cnt=7&APPID=91f5322935fb7f5529e91a3d27681110";
            URL u1 = new URL(forecast_url_string);
            Log.v("Connection url: ", forecast_url_string);

            connection = (HttpURLConnection) u1.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            is1 = connection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is1));

            StringBuffer buffer = new StringBuffer();

            String line = null;

            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }

            return buffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is1 != null) {
                try {
                    is1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null)
                connection.disconnect();
        }

        return null;
    }
}
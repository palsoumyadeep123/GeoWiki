package com.example.soumyadeeppal.geowiki;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Utility class for parsing JSON String returned on querying for current weather conditions in search city ( API : openweathermaps.org)
 */
public class WeatherJSONStringParser {

    public Weather parse(String JSONString){

        Log.v("JSONString in parse: ", JSONString);

        JSONObject initial;
        JSONObject coord;
        JSONObject main;
        JSONObject wind;
        JSONObject clouds;
        JSONObject sys;
        JSONArray weather;
        String result="";
        Weather w=new Weather();

        try {



                initial = new JSONObject(JSONString);
            if (initial.getInt("cod")!=404) {
                coord = initial.getJSONObject("coord");
                weather = initial.getJSONArray("weather");
                main = initial.getJSONObject("main");
                wind = initial.getJSONObject("wind");
                clouds = initial.getJSONObject("clouds");
                sys = initial.getJSONObject("sys");


                Log.v("JSON Temp : ", (weather.getJSONObject(0)).getString("description"));


                result = "Latitude : " + coord.getDouble("lat") + "  |  " + "Longitude :" + coord.getDouble("lon") + "  |  " + "City : " + initial.getString("name") + "  |  " + "Country : " + sys.getString("country") + "  |  " + "Max Temp : " + main.getDouble("temp_max") + "  |  " + "Min temp :" + main.getDouble("temp_min") + "  |  " + "Weather Description : " + weather.getJSONObject(0).getString("description");

                //for coord JSONObject

                w.setCloudiness(clouds.getDouble("all"));
                w.setSunrise(sys.getLong("sunrise"));

                w.setSunset(sys.getLong("sunset"));

                w.setDate(initial.getLong("dt"));

                w.setLat(coord.getDouble("lat"));

                w.setLon(coord.getDouble("lon"));

                // for weather JSONArray

                w.setWeather_description((weather.getJSONObject(0)).getString("description"));

                w.setWeatherid(weather.getJSONObject(0).getInt("id"));

                w.setWeathericon(weather.getJSONObject(0).getString("icon"));


                // for main JSONObject
                w.setAvg_temp(main.getDouble("temp"));
                w.setMax_temp(main.getDouble("temp_min"));
                w.setMin_temp(main.getDouble("temp_max"));
                w.setPressure(main.getDouble("pressure"));
                w.setHumidity(main.getDouble("humidity"));

                //for wind JSONObject

                w.setWind_degree(wind.getDouble("deg"));
                w.setWind_speed(wind.getDouble("speed"));

                // for initial JSONObject

                w.setCity_name(initial.getString("name"));
                w.setCountry(sys.getString("country"));

                String msg = w.avg_temp + "\t" + w.wind_speed;

                Log.v("Weather object data : ", msg);

                return w;
            }
            else
            {
                Log.v("ConnectionError","404 error");
            }

    }catch (JSONException e){
            e.printStackTrace();
        }

        return null;
    }
}

package com.example.soumyadeeppal.geowiki;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *  Utility class for parsing JSON String returned on querying for 5 day weather forecast ( API : openweathermaps.org)
 */
public class ForecastJSONStringParser {

    JSONObject init;
    JSONArray list;


    public Forecast[] doParse(String forecastJSONString)
    {

        Log.v("JSONString in doParse",forecastJSONString);

        try {
            init=new JSONObject(forecastJSONString);
            if (init.getString("cod").equals("404")==false) {

                list = init.getJSONArray("list");

                // for debug purposes
                Log.v("Clouds for day 0",""+list.getJSONObject(0).getDouble("clouds"));
                Log.v("List length: ",""+list.length());


                Forecast objects[] = new Forecast[list.length()];


                for (int i = 0; i < list.length(); i++) {

                    JSONObject temp;
                    JSONArray weather;
                    JSONObject listObject = list.getJSONObject(i);

                    temp = listObject.getJSONObject("temp");
                    weather = listObject.getJSONArray("weather");
                    
                    Forecast ob=new Forecast();
                    


                    ob.setClouds(listObject.getInt("clouds"));
                    ob.setDate(listObject.getLong("dt"));
                    ob.setHumidity(listObject.getDouble("humidity"));
                    ob.setIcon_name(weather.getJSONObject(0).getString("icon"));
                    ob.setTemp_day(temp.getDouble("day"));
                    ob.setTemp_min(temp.getDouble("min"));
                    ob.setTemp_max(temp.getDouble("max"));
                    ob.setTemp_night(temp.getDouble("night"));
                    ob.setTemp_eve(temp.getDouble("eve"));
                    ob.setTemp_morn(temp.getDouble("morn"));
                    ob.setWeather_desc(weather.getJSONObject(0).getString("description"));
                    ob.setWind_speed(listObject.getDouble("speed"));
                    ob.setWind_degree(listObject.getDouble("deg"));
                    ob.setPressure(listObject.getDouble("pressure"));

                    objects[i]=ob;
                }
                return objects;

            }
            else
            {
                Log.v("Connection Error:","404 error");
            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }


        return null;
    }
}

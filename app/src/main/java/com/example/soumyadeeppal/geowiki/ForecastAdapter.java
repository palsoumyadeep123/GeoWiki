package com.example.soumyadeeppal.geowiki;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Adapter class for storing weather data for each day of 5 day forecast displayed via ListView in DisplayForecast activity
 */
public class ForecastAdapter extends BaseAdapter {

    Context c;
    Forecast forecast[];
    //String unit;

    LayoutInflater inflater;

    ForecastAdapter(Context c,Forecast forecast[])
    {
        this.c=c;
        //this.unit=unit;
        this.forecast=forecast;
        inflater=LayoutInflater.from(c);
    }
    @Override
    public int getCount() {
        return forecast.length;
    }

    @Override
    public Object getItem(int position) {
        return forecast[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.forecastelement, null);

            ImageView iv = (ImageView) convertView.findViewById(R.id.imageView);

            TextView tv = (TextView) convertView.findViewById(R.id.textView3);

            SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(c);

            String unit=sp.getString("temp_pref","Celcius");

            if (unit.equals("Celcius") == true) {


                tv.setText("Date :" + forecast[position].date + " | Weather Desc :" + forecast[position].weather_desc + " | Day Temp:" + (new DecimalFormat("##.#").format(forecast[position].temp_day)) + "\u2103" + " | Night Temp:" +
                        (new DecimalFormat("##.#").format(forecast[position].temp_night) + "\u2103")
                        + " | Humidity(in %):" + forecast[position].humidity + " | Cloudiness(in %):" + forecast[position].clouds);


            }
            else
            {
                double fnight=32+((9*forecast[position].temp_night)/5);
                double fday=32+((9*forecast[position].temp_day)/5);


                tv.setText("Date :" + forecast[position].date + " | Weather Desc :" + forecast[position].weather_desc + " | Day Temp:" + (new DecimalFormat("##.#").format(fday)) + "\u2109" + " | Night Temp:" +
                        (new DecimalFormat("##.#").format(fnight) + "\u2109")
                        + " | Humidity(in %):" + forecast[position].humidity + " | Cloudiness(in %):" + forecast[position].clouds);

            }
            String image[] = new String[1];
            image[0] = forecast[position].icon_name;
            new FetchForecastImage(iv).execute(image);


        }
        return convertView;
    }



    public class FetchForecastImage extends AsyncTask<String,Void,Bitmap>
    {
        ImageView iv1;

        FetchForecastImage(ImageView iv)
        {
            this.iv1=iv;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            Bitmap b=new WeatherClient().getWeatherImage(params[0]);
            return b;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            iv1.setImageBitmap(bitmap);

        }
    }
}


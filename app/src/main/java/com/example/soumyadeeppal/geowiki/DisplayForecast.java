package com.example.soumyadeeppal.geowiki;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/*
    Activity to display 5 day weather forecast for current search city
 */

public class DisplayForecast extends AppCompatActivity {
    TextView tv;

    WeatherForecastTask f_task;
    String city;
    String query[];
    ConnectivityManager manager;
    Forecast f_cast[];
    String forecastJSONString;

    ForecastAdapter adapter;

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_forecast);
        city=getIntent().getStringExtra("location");

        lv=(ListView)findViewById(R.id.listView2);



        tv=(TextView)findViewById(R.id.textView3);
        manager=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);

        if (manager.getActiveNetworkInfo()!=null && manager.getActiveNetworkInfo().isConnected()==true) {

            if (city.equals("") == false) {
                query = new String[1];
                query[0] = city;

                f_task = new WeatherForecastTask();
                f_task.execute(query);
            } else {
                Toast.makeText(DisplayForecast.this, "Please press back and type city name in search bar", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(DisplayForecast.this,"Please Connect Internet to access Forecast Info",Toast.LENGTH_LONG).show();

        }
    }


    public class WeatherForecastTask extends AsyncTask<String,Void,Forecast[]> {

        @Override
        protected Forecast[] doInBackground(String... params) {
            forecastJSONString = new ForecastClient().getForecastData(params[0]);
            Log.v("Forecast JSONString : ", forecastJSONString);
            f_cast = new ForecastJSONStringParser().doParse(forecastJSONString);
            return f_cast;
        }

        @Override
        protected void onPostExecute(final Forecast[] forecasts) {
            super.onPostExecute(forecasts);
            if (forecasts != null) {

                SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                adapter = new ForecastAdapter(getBaseContext(), forecasts);
                lv.setAdapter(adapter);
            } else {
                lv.invalidate();
                tv.setText("Error connecting to server/ City Not Found");
            }
        }
    }
}

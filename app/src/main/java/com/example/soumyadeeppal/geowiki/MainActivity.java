package com.example.soumyadeeppal.geowiki;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.DecimalFormat;

/*
        Activity Class for displaying current weather conditions for search city
 */

public class MainActivity extends AppCompatActivity {

    Button scan;
    LocationManager lm;
    ListView lv;
    float lat, lng;
    MyFragment f;
    Double data1[];
    String arr[];
    myAdapter adapter;

    ConnectivityManager manager;

    NetworkInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        lv=(ListView)findViewById(R.id.listView);
        arr=new String[1];

        manager=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.myoptionmenu, menu);




        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView sv = (SearchView) MenuItemCompat.getActionView(searchItem);
        sv.setQueryHint("Enter city name (e.g. Kolkata)");

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                arr[0] = query;

                info = manager.getActiveNetworkInfo();
                if (info != null && info.isConnected() == true) {
                    WeatherDataTask newtask = new WeatherDataTask();
                    newtask.execute(arr);
                } else {
                    Toast.makeText(MainActivity.this, "Please Connect to Internet to access weather info", Toast.LENGTH_LONG).show();
                }
                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

        });

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.settings:
                Intent i=new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(i);
                break;

            case R.id.forecast_list:
                Intent i1=new Intent(MainActivity.this,DisplayForecast.class);
                if (arr[0]!=null) {
                    i1.putExtra("location", arr[0]);
                    startActivity(i1);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please enter city in search bar", Toast.LENGTH_SHORT).show();
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }



    public class WeatherDataTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            String weatherJSONString = null;
            Weather weather;


            try {

                weatherJSONString = (new WeatherClient()).getWeatherData(params[0]);
                Log.v("WEATHERJSONSTRING : ", weatherJSONString);
            } catch (IOException e) {
                e.printStackTrace();
            }


            WeatherJSONStringParser weatherJSONStringParser = new WeatherJSONStringParser();
            weather = weatherJSONStringParser.parse(weatherJSONString);



                if (weather!=null) {
                    weather.setWeather_image ((new WeatherClient()).getWeatherImage(weather.getWeather_icon()));
                } else {
                    Log.v("WeatherObjectError :", "Weather object not created");
                }


            return weather;
        }

        @Override
        protected void onPostExecute(final Weather weather) {
            super.onPostExecute(weather);

            f=(MyFragment)getFragmentManager().findFragmentById(R.id.fragment);
            if (weather != null) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String unit=sp.getString("temp_pref","Celcius");
                if (unit.equals("Celcius")) {
                    f.setText1("" + new DecimalFormat("##.#").format(weather.avg_temp) + "\u2103");
                }
                else
                {
                    double ftemp=32+((9*weather.avg_temp)/5);
                    f.setText1("" + new DecimalFormat("##.#").format(ftemp) + "\u2109");

                }
                f.setImage(weather.weather_image);
                f.setText2("Description : " + weather.weather_description + "  |  City :" + weather.city_name + " | Country :" + weather.country + " | Date :" + weather.date + " | Sunrise:" + weather.sunrise + " | Sunset:" + weather.sunset);

                data1 = new Double[10];
                data1[0] = weather.lat;
                data1[1] = weather.lon;

                data1[2] = weather.avg_temp;
                data1[3] = weather.max_temp;
                data1[4] = weather.min_temp;

                data1[5] = weather.humidity;
                data1[6] = weather.wind_speed;
                data1[7] = weather.wind_degree;
                data1[8] = weather.pressure;
                data1[9] = weather.cloudiness;



                adapter = new myAdapter(getBaseContext(), data1);
                lv.setAdapter(adapter);


                sp.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                        lv.invalidate();
                        if (sharedPreferences.getString(key,"Celcius").equals("Celcius"))
                        {
                            f.setText1(""+ new DecimalFormat("##.#").format(weather.avg_temp)+"\u2103");
                        }
                        else
                        {
                            f.setText1(""+ new DecimalFormat("##.#").format(32+((9*weather.avg_temp)/5))+"\u2109");

                        }

                        adapter=new myAdapter(getBaseContext(),data1);
                        lv.setAdapter(adapter);
                    }
                });
            }
            else
            {
                f.setText2("Connection Refused by server/ Wrong city name");
                lv.invalidate();
            }
        }
    }
}
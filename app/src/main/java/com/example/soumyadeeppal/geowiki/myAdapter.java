package com.example.soumyadeeppal.geowiki;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

/*
        Adapter class for holding current weather data for ListView in MainActivity
 */


public class myAdapter extends BaseAdapter {

    Double data[];
    Context c;

    LayoutInflater inflater;


    myAdapter(Context context, Double data[])
    {
        this.data=data;
        this.c=context;

        inflater=LayoutInflater.from(c);
    }
    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=inflater.inflate(R.layout.listelement,null);

        TextView parameter=(TextView)convertView.findViewById(R.id.textView);

        String unit=PreferenceManager.getDefaultSharedPreferences(c).getString("temp_pref","Celcius");

        TextView value=(TextView)convertView.findViewById(R.id.textView2);
        switch(position)
        {
            case 0:
                parameter.setText("Latitude:");
                value.setText(""+data[0]);
                break;
            case 1:
                parameter.setText("Longitude:");
                value.setText(""+data[1]);
                break;
            case 2:
                if (unit.equals("Celcius")) {
                    parameter.setText("Average Temperature(in \u2103):");
                    value.setText("" + data[2]);
                }
                else
                {
                    parameter.setText("Average Temperature(in \u2109):");
                    value.setText("" + (32+((9*data[2])/5)));

                }
                break;
            case 3:
                if (unit.equals("Celcius")) {
                    parameter.setText("Maximum Temperature(in \u2103):");
                    value.setText("" + data[3]);
                }
                else
                {
                    parameter.setText("Maximum Temperature(in \u2109):");
                    value.setText("" + (32+((9*data[3])/5)));

                }
                break;
            case 4:
                if (unit.equals("Celcius")) {
                    parameter.setText("Minimum Temperature(in \u2103):");
                    value.setText("" + data[4]);
                }
                else
                {
                    parameter.setText("Minimum Temperature(in \u2109):");
                    value.setText("" + (32+((9*data[4])/5)));

                }
                break;
            case 5:
                parameter.setText("Humidity(in %):");
                value.setText(""+data[5]);
                break;
            case 6:
                parameter.setText("Wind Speed(n metres/sec):");
                value.setText(""+data[6]);
                break;
            case 7:
                parameter.setText("Wind Degree(in degrees):");
                value.setText(""+data[7]);
                break;
            case 8:
                parameter.setText("Pressure(in hPa):");
                value.setText(""+data[8]);
                break;
            case 9:
                parameter.setText("Cloudiness(in %):");
                value.setText(""+data[9]);
                break;

        }

        return convertView;
    }
}

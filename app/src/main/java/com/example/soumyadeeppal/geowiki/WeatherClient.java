package com.example.soumyadeeppal.geowiki;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *      Utility class handling HTTP connection to openweathermaps.org API for retreiving current weather data
 */
public class WeatherClient {
    String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?";
    String APP_ID = "91f5322935fb7f5529e91a3d27681110";

    String IMAGE_URL="http://openweathermap.org/img/w/";



    String weatherJSONString;
    byte array[];
    ByteArrayOutputStream bastream;


    public String getWeatherData(String city) throws IOException
    {
        HttpURLConnection conn = null;
        InputStream is = null;

        String myURL=BASE_URL + "q="+city+ "&APPID=" + APP_ID;

        URL url = new URL(myURL);

        Log.v("URL : ",myURL);


        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            //conn.setDoOutput(true);
            conn.connect();

            is = conn.getInputStream();

            StringBuffer buffer = new StringBuffer();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line=null;

            while (  (line = br.readLine()) != null ) {
                buffer.append(line);
            }


            weatherJSONString = buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
                is.close();
                conn.disconnect();
        }

        return weatherJSONString;

    }


    public Bitmap getWeatherImage(String icon_string){

        HttpURLConnection conn = null;
        InputStream is = null;
        try {
            URL url2 = new URL(IMAGE_URL + icon_string+".png");
            Log.v("Image URl : ",IMAGE_URL + icon_string+".png");
            conn = (HttpURLConnection) url2.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            //conn.setDoOutput(true);
            conn.connect();


            is = conn.getInputStream();

            /*bastream = new ByteArrayOutputStream();
            byte arr[] = new byte[1024];
            while (is.read(arr) != -1) {
                bastream.write(arr);
            }*/


            BufferedInputStream bis=new BufferedInputStream(is);

            Bitmap bmp= BitmapFactory.decodeStream(is);

            return bmp;


        }catch(Exception e){
            e.printStackTrace();
        }

        finally {
            try {
                if (is!=null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if(conn!=null)
                    conn.disconnect();

            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return null;
    }

}

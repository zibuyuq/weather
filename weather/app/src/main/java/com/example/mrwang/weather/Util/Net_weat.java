package com.example.mrwang.weather.Util;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.mrwang.weather.gson.Weather;


public class Net_weat {
    public  StringBuilder response;
    public  Weather weather;
    public  Bitmap bm;
    public  HttpURLConnection connection;
    public Gson_u gson_u_ = new Gson_u();
      public void net_Weather(final Handler handler,final String weather_id)
      {

                new Thread(new Runnable() {
                    @Override
                    public void run()
                    {
                        try {
                            URL url = new URL("http://guolin.tech/api/weather?cityid=" + weather_id + "&key=bc0418b57b2d4918819d3974ac1285d9");
                            connection = (HttpURLConnection)url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.setReadTimeout(8000);
                            connection.setReadTimeout(8000);
                            InputStream inputStream = connection.getInputStream();
                            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            response = new StringBuilder();
                            String line;
                            if(connection.getResponseCode() == 200) {
                                while ((line = bufferedReader.readLine()) != null) {
                                    response.append(line);
                                }
                                weather = gson_u_.parseWeatherResponse(response.toString());

                                Message msg = Message.obtain();
                                msg.obj=weather;
                                msg.what=5;
                                handler.sendMessage(msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        finally {
                            if (connection != null) {
                                connection.disconnect();
                            }
                        }
                    }

       }).start();
  }
}


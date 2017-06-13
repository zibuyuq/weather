package com.example.mrwang.weather.Util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;



public class Net_wz {
    public Context context;
    public ArrayList<String> list =new ArrayList<>();
    public ArrayList<String> listId =new ArrayList<>();
    public ArrayList<String> weatherId =new ArrayList<>();
    public StringBuilder response;
    public void net(final Handler handler,final String address,final String address1)
    {
        new Thread(new Runnable() {
            @Override
            public void run () {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://guolin.tech/api/china/" + address + "/" + address1);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    response = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    ;
                    JSONArray array = new JSONArray(response.toString());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        int provincecode = obj.getInt("id");
                        String provincename = obj.getString("name");
                        if (!TextUtils.isEmpty(address1)) {
                            String weather_Id = obj.getString("weather_id");
                            weatherId.add(weather_Id + "");
                        }

                        listId.add(provincecode + "");
                        list.add(provincename);
                        if(TextUtils.isEmpty(address) && TextUtils.isEmpty(address1))
                        {
                            Message msg = Message.obtain();
                            msg.obj=list;
                            msg.what=1;
                            handler.sendMessage(msg);
                        }
                        else if(!TextUtils.isEmpty(address) && TextUtils.isEmpty(address1))
                        {
                            HashMap<String,ArrayList<String>> hashMap = new HashMap<String, ArrayList<String>>();
                            hashMap.put("list",list);
                            hashMap.put("listId",listId);
                            Message msg = Message.obtain();
                            msg.obj=hashMap;
                            msg.what=3;
                            handler.sendMessage(msg);
                        }
                        else
                        {
                            HashMap<String,ArrayList<String>> hashMap = new HashMap<String, ArrayList<String>>();
                            hashMap.put("list",list);
                            hashMap.put("weather_id",weatherId);
                            Message msg = Message.obtain();
                            msg.obj=hashMap;
                            msg.what=6;
                            handler.sendMessage(msg);
                        }

                    }
                } catch (Exception e) {

                    e.printStackTrace();
                } finally {
                    if (connection != null) {;
                        connection.disconnect();
                    }
                }
            }
        }).start();

    }



}



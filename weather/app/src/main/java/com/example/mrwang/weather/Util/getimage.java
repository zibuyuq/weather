package com.example.mrwang.weather.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import com.example.mrwang.weather.gson.Forecast;



public class getimage {
    public Bitmap bmp = null;
    public HttpURLConnection connection;
    HashMap<String,Bitmap> hashMap = new HashMap<String,Bitmap>();
    public int isOk = 0;
    int i;
    public void net_img(final Handler handler, final String code,final String position)
    {
        new Thread(new Runnable() {
            @Override
                public void run() {
                    try {
                        URL myurl = new URL("https://cdn.heweather.com/cond_icon/" + code + ".png");
                        // 获得连接
                        connection = (HttpURLConnection) myurl.openConnection();
                        connection.setConnectTimeout(6000);//设置超时
                        connection.setDoInput(true);
                        connection.setUseCaches(false);//不缓存
                        connection.connect();
                        InputStream is = connection.getInputStream();//获得图片的数据流
                        bmp = BitmapFactory.decodeStream(is);
                        HashMap<String,Bitmap> hashMap = new HashMap<String,Bitmap>();
                        if(TextUtils.isEmpty(position+"")) {
                            hashMap.put("Main", bmp);
                            Message msg = Message.obtain();
                            msg.obj = hashMap;
                            msg.what = 4;
                            handler.sendMessage(msg);
                        }
                        else {
                            hashMap.put(position, bmp);
                            Message msg = Message.obtain();
                            msg.obj = hashMap;
                            msg.what = Integer.parseInt(position);
                           handler.sendMessage(msg);
                        }

                        is.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (connection != null) {
                            connection.disconnect();
                        }
                    }
                }
        }).start();
    }

    public void net_imgs(final Handler handler, final List<Forecast> code)
    {



            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for(i=0;i<code.size()-3;i++) {
                            URL myurl = new URL("https://cdn.heweather.com/cond_icon/" + code.get(i).more.tb.toString() + ".png");
                            // 获得连接
                            connection = (HttpURLConnection) myurl.openConnection();
                            connection.setConnectTimeout(6000);//设置超时
                            connection.setDoInput(true);
                            connection.setUseCaches(false);//不缓存
                            connection.connect();
                            InputStream is = connection.getInputStream();//获得图片的数据流
                            bmp = BitmapFactory.decodeStream(is);
                            hashMap.put(i + "", bmp);
                            is.close();
                        }
                        Message msg = Message.obtain();
                        msg.obj = hashMap;
                        handler.sendMessage(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (connection != null) {

                            connection.disconnect();
                        }
                    }
                }
            }).start();
        }



}


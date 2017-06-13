package com.example.mrwang.weather.myAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.mrwang.weather.R;
import com.example.mrwang.weather.gson.Forecast;


public class Adapter extends BaseAdapter{
    private List<Forecast> list;
    private Context context;
    private TextView textView;
    private TextView textView1;
    private TextView textView2;
    private ImageView imageView;
    private ArrayList<Bitmap> img;
    private int Listposition;
    View view = null;
    public Adapter(Context context, List<Forecast> list, ArrayList<Bitmap> img){
        this.img= img;
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
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

        if(convertView != null)
        {
            view = convertView;
        }
        else{
            view = View.inflate(context, R.layout.item,null);
        }
         final Forecast forecast = list.get(position);

        Listposition = position;


         textView = (TextView) view.findViewById(R.id.item_tv_title);
         textView1 = (TextView) view.findViewById(R.id.item_tv_title1);
         textView2 = (TextView) view.findViewById(R.id.item_tv_des);
         imageView = (ImageView) view.findViewById(R.id.tb_list);
         imageView.setImageBitmap(img.get(position));
         textView.setText(forecast.date.toString());
         textView1.setText(forecast.more.info);
         textView2.setText(forecast.temperature.max+"°"+"/"+forecast.temperature.min+"°");

        return view;
    }

}

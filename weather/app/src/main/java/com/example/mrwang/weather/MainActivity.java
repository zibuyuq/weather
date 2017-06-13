package com.example.mrwang.weather;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.example.mrwang.weather.Util.Net_weat;
import com.example.mrwang.weather.Util.Net_wz;
import com.example.mrwang.weather.Util.getimage;
import com.example.mrwang.weather.gson.Weather;
import com.example.mrwang.weather.myAdapter.Adapter;

import static com.example.mrwang.weather.R.id.aqi;
import static com.example.mrwang.weather.R.id.b;
import static com.example.mrwang.weather.R.id.tmp;
import static com.example.mrwang.weather.R.id.zl;

public class MainActivity extends AppCompatActivity {


    private ImageView imageView;

    private Bitmap btimap;

    private long last = 0;

    private SharedPreferences sp;

    private Context mContest;

    private Adapter adapter;

    public Weather weather;

    private ImageButton btn1;

    private int location = 35, width,sPposition;

    private long min_time = 300;

    private ListView l_province, l_city, l_county, item;

    private ArrayAdapter<String> adp_province, adp_city, adp_county, adp_arr;

    private String w_id, address, sPosi, weatherid, addressid, addresss, add_id;

    private List<String> ls_province, ls_city, ls_cityId, ls_county, List_weatherid, ls_spinner,ls_saveWeatherid,ls_saveCity;

    private TextView T_tmp, T_city, T_wind, T_xd, T_wd, T_zd, T_zl, T_aqi, T_pm, T_suggestion;

    //用于省，市，县的滑入滑出判断
    private boolean Null = true;
    private boolean Null1 = false;
    private boolean Null2 = true;
    private boolean Once = false;
    private boolean Once1 = false;
    private boolean Once2 = false;
    private boolean Once3 = false;
    private boolean Once4 = false;
    private boolean Once5 = false;
    private boolean isOk = true;

    //Handler操作
    private boolean isT = true;
    private boolean isT1 = true;
    private boolean isT2 = true;
    private boolean isClick = true;
    private boolean isClick_province = false;
    private boolean isClick_city = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowManager wm = this.getWindowManager();
        width = wm.getDefaultDisplay().getWidth();


        l_province = (ListView) findViewById(R.id.list);
        l_city = (ListView) findViewById(R.id.list1);
        l_county = (ListView) findViewById(R.id.list2);
        item = (ListView) findViewById(R.id.item);
        btn1 = (ImageButton) findViewById(b);
        T_tmp = (TextView) findViewById(tmp);
        T_city = (TextView) findViewById(R.id.text);
        T_wind = (TextView) findViewById(R.id.text1);
        T_xd = (TextView) findViewById(R.id.text4);
        T_wd = (TextView) findViewById(R.id.text5);
        T_zd = (TextView) findViewById(R.id.text6);
        T_zl = (TextView) findViewById(zl);
        T_aqi = (TextView) findViewById(aqi);
        T_pm = (TextView) findViewById(R.id.pm25);
        T_suggestion = (TextView) findViewById(R.id.suggesstion);
        imageView = (ImageView) findViewById(R.id.tb);


        mContest = this;
        sp = mContest.getSharedPreferences("weather", MODE_PRIVATE);
        weatherid = sp.getString("w_id", "CN101010100");
        addresss = sp.getString("address", "1");
        add_id = sp.getString("addressid", "1");
        sPosi = sp.getString("sPposition", "0");
        if (checkNetworkAvailable(MainActivity.this)) {
                sPposition = Integer.parseInt(sPosi);
                Net_wz netWz = new Net_wz();
                netWz.net(handler1,address,add_id);
                Net_weat net_weat = new Net_weat();
                net_weat.net_Weather(handler,weatherid);

        }
        else
        {
            Toast.makeText(mContest, "未检测到网络!", Toast.LENGTH_SHORT).show();
            isClick = false;
        }

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long cTime = Calendar.getInstance().getTimeInMillis();
                if(cTime - last > min_time) {
                if (checkNetworkAvailable(MainActivity.this)) {
                    Net_wz netWz = new Net_wz();
                    netWz.net(handler1,"","");
                    if (!Once) {
                        if (isOk) {
                            isT1 = true;
                            isOk = false;
                            ObjectAnimator.ofFloat(l_province, "translationX", 0, width/4+width/35).setDuration(100).start();
                            ObjectAnimator.ofFloat(btn1, "rotation", 0, 90).setDuration(100).start();
                        } else {
                            ObjectAnimator.ofFloat(l_province, "translationX", 0, width/4+width/35).setDuration(100).start();
                            ObjectAnimator.ofFloat(btn1, "rotation", 0, 90).setDuration(100).start();
                        }
                        Once = true;
                    }
                    else {
                        ObjectAnimator.ofFloat(l_province, "translationX", width/4+width/35, 0).setDuration(100).start();
                        ObjectAnimator.ofFloat(btn1, "rotation", 90, 0).setDuration(100).start();
                        if (ls_city != null && Once5 && !Once2) {
                            ObjectAnimator.ofFloat(l_city, "translationX", (width/4+width/35)*2, 0).setDuration(100).start();
                            Once5 = false;
                            Once3 = false;
                            Once1 = false;
                            Once2 = true;
                            Once = false;
                            location = 35;
                        }
                        if (ls_county != null && Once4 && Null1) {
                            ObjectAnimator.ofFloat(l_county, "translationX", (width/4+width/35)*3, 0).setDuration(100).start();
                            Once4 = false;
                            Once3 = false;
                            Once1 = false;
                            Once2 = true;
                            Null1 = false;
                            Null2 = true;
                            Once = false;
                        }
                       Once = false;
                    }

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "未检测到网络！", Toast.LENGTH_LONG).show();

                    }
                }
            }
        });

        l_province.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long cTime = Calendar.getInstance().getTimeInMillis();
                if (checkNetworkAvailable(MainActivity.this)) {
                    //开启一个新线程
                    isT1 = true;
                    Net_wz netWz = new Net_wz();
                    netWz.net(handler1,(position + 1) + "","");
                    if (cTime - last > min_time) {
                        if (ls_county != null && Null1) {
                            ObjectAnimator.ofFloat(l_county, "translationX", (width / 4 + width / 35) * 3, 0).setDuration(100).start();

                            Null1 = false;
                            Null2 = true;
                        }
                        if (ls_city != null) {
                            //  Null用来展开市l_city
                            // isMove用来判断是否刷新l_city数据
                            if (location == position) { //判断2次点击是否是同一个

                                if (!Once2) { //Once2是用来判断是第一次点击还是第二次点击（区分点击）
                                    {
                                        ObjectAnimator.ofFloat(l_city, "translationX", (width / 4 + width / 35) * 2, 0).setDuration(100).start();
                                        Once3 = true;//用来判断是否是不是该展开l_city
                                    }
                                    Once2 = true;
                                }
                                Null = false;
                                Once1 = false;

                            } else if (Once1) {//当Once1为true时表示市l_city为展开状态，false则相反
                                Null = false;
                                Once2 = false;
                            } else {
                                Null = true;
                                Once2 = false;
                            }
                        }
                        if (Null) {
                            isClick_province = true;
                            Once1 = true;
                            Once5 = true;
                        }
                        if (!Once3)
                            location = position;//该展开l_city
                        else {//不该展开l_city
                            location = 35;
                            Once3 = false;
                        }
                        last = cTime;
                    }



                } else Toast.makeText(getApplicationContext(), "未检测到网络！", Toast.LENGTH_LONG).show();
            }
        });

        l_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (checkNetworkAvailable(MainActivity.this)) {

                    if (Null1 && ls_county != null) {
                        Null2 = false;
                    }
                    isT1 = true;
                    Null1 = true;
                    Net_wz netWz = new Net_wz();
                    netWz.net(handler1,address,ls_cityId.get(position).toString());
                    addressid = ls_cityId.get(position).toString();
                            if (Null2) {
                               isClick_city = true;
                                Once4 = true;
                            }

                        } else Toast.makeText(getApplicationContext(), "未检测到网络！", Toast.LENGTH_LONG).show();

             }
         });


        l_county.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (checkNetworkAvailable(MainActivity.this)) {
                    isT = true;
                    isT2 = true;
                    sPposition = position;
                    Null2 = true;
                    ls_spinner = List_weatherid = ls_saveWeatherid;
                    adp_arr = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, ls_saveCity);
                    //设置样式
                    adp_arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Net_weat net_weat = new Net_weat();
                    net_weat.net_Weather(handler,List_weatherid.get(position));
                    w_id = List_weatherid.get(position);
                                ObjectAnimator.ofFloat(l_province, "translationX", 0).setDuration(0).start();
                                ObjectAnimator.ofFloat(l_city, "translationX", 0).setDuration(0).start();
                                ObjectAnimator.ofFloat(l_county, "translationX", 0).setDuration(0).start();
                                ObjectAnimator.ofFloat(btn1, "rotation", 90, 0).setDuration(0).start();
                                Once3 = false;
                                location = 35;
                                Once1 = false;
                                Once2 = true;
                                Null1 = false;
                                Once = false;
                                Once4 = false;
                                Once5 = false;
                                sp = mContest.getSharedPreferences("weather", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("sPposition", sPposition+"");
                                editor.putString("w_id", w_id);
                                editor.putString("addressid", addressid);
                                editor.putString("address", address);
                                editor.commit();
                }else Toast.makeText(getApplicationContext(), "未检测到网络！", Toast.LENGTH_LONG).show();
            }
        });
    }

    Handler handler1 = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if(isT1)
            {
                switch (msg.what)
                {
                    case 1:
                        ls_province = (ArrayList<String>)msg.obj;
                        adp_province = new ArrayAdapter<String>(MainActivity.this, R.layout.listview, ls_province);
                        l_province.setAdapter(adp_province);
                        isT1 = false;
                        break;
                    case 3:
                        HashMap<String,ArrayList<String>> hashMap = new HashMap<String, ArrayList<String>>();
                        hashMap=(HashMap<String, ArrayList<String>>) msg.obj;
                        ls_city = hashMap.get("list");
                        ls_cityId = hashMap.get("listId");
                        adp_city = new ArrayAdapter<String>(MainActivity.this, R.layout.listview, ls_city);
                       l_city.setAdapter(adp_city);
                        if(isClick_province)
                        {
                            ObjectAnimator.ofFloat(l_city, "translationX", 0,(width / 4 + width / 35) * 2).setDuration(100).start();
                            isClick_province= false;
                        }
                        isT1 = false;
                        break;
                    case 6:
                        HashMap<String,ArrayList<String>> hashMap1 = new HashMap<String, ArrayList<String>>();
                        hashMap1=(HashMap<String, ArrayList<String>>) msg.obj;
                        ls_county = hashMap1.get("list");
                        adp_county = new ArrayAdapter<String>(MainActivity.this, R.layout.listview, ls_county);
                        l_county.setAdapter(adp_county);
                        ls_saveWeatherid = hashMap1.get("weather_id");
                        List_weatherid = ls_saveWeatherid;
                        ls_saveCity = ls_county;
                        if(isClick_city)
                        {
                            ObjectAnimator.ofFloat(l_county, "translationX", 0, (width / 4 + width / 35) * 3).setDuration(100).start();
                            isClick_city= false;
                        }
                        isT1 = false;
                        break;

                }
        }
      };
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(isT){
                if(msg.what==5)
                weather = (Weather) msg.obj;
                if (weather.now == null) {
                    Toast.makeText(mContest, "Sorry!没有此城市的天气预报", Toast.LENGTH_SHORT).show();
                    isT=false;

                } else {

                    getimage getimage = new getimage();
                    getimage getimage1 = new getimage();
                    getimage.net_img(handler2,weather.now.more.tubiao,"");
                    getimage1.net_imgs(handler3,weather.forecastList);
                    isT=false;
                }
            };
        }
    };

    Handler handler2 = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if(isT2) {
                HashMap<String, Bitmap> hashMap1 = new HashMap<String, Bitmap>();
                if (msg.what == 4)
                    hashMap1 = (HashMap<String, Bitmap>) msg.obj;
                    btimap = hashMap1.get("Main");

                    isT2=false;
            }

        };
    };

    Handler handler3 = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            HashMap<String,Bitmap> hashMap = new HashMap<String,Bitmap>();
            ArrayList<Bitmap> img =new ArrayList<Bitmap>();


            if (!TextUtils.isEmpty(weather.now.temperature))
                imageView.setImageBitmap(btimap);
                T_tmp.setText(weather.now.temperature + "°");
            T_city.setText(weather.basic.cityName + "|" + weather.now.more.info);
            T_wind.setText(weather.now.other.wind);
            T_xd.setText(weather.now.other.fd + "级");
            T_wd.setText(weather.now.sd + "%");
            T_zd.setText(weather.now.tg + "°");
            if (weather.aqi != null)
                T_zl.setText(weather.aqi.city.qlty);
            else T_zl.setText("无");
            if (weather.aqi != null)
                T_aqi.setText(weather.aqi.city.aqi);
            else T_aqi.setText("无");
            if (weather.aqi != null)
                T_pm.setText(weather.aqi.city.pm25);
            else T_pm.setText("无");
            T_suggestion.setText(weather.suggestion.sport.info);
            hashMap=(HashMap<String, Bitmap>) msg.obj;
            Bitmap bitmap = hashMap.get("0");
            Bitmap bitmap1 = hashMap.get("1");
            Bitmap bitmap2 = hashMap.get("2");
            Bitmap bitmap3 = hashMap.get("3");
            img.add(0,bitmap);
            img.add(1,bitmap1);
            img.add(2,bitmap2);
            img.add(3,bitmap3);

            adapter = new Adapter(mContest, weather.forecastList,img);
            item.setAdapter(adapter);
            setListViewHeightBasedOnChildren(item);
        }
    };

    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        // listAdapter.getCount()返回数据项的数目
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    public static boolean checkNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        NetworkInfo netWorkInfo = info[i];
                        if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            return true;
                        } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}



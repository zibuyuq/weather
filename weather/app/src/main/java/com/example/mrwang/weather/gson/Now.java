package com.example.mrwang.weather.gson;

import com.google.gson.annotations.SerializedName;

public class Now {

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    @SerializedName("wind")
    public  Other other;

    @SerializedName("fl")
    public  String tg;

    @SerializedName("hum")
    public  String sd;

    public class Other{
        @SerializedName("dir")
        public String wind;
        @SerializedName("sc")
        public  String fd;
    }

    public class More {

        @SerializedName("txt")
        public String info;

        @SerializedName("code")
        public String tubiao;

    }

}

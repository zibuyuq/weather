package com.example.mrwang.weather.gson;

import com.google.gson.annotations.SerializedName;



public class Province {
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    public Province(){}
    public Province(String id,String name){
        this.id=id;
        this.name=name;
    }
}

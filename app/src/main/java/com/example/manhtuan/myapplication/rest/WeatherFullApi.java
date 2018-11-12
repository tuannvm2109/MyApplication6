package com.example.manhtuan.myapplication.rest;

public class WeatherFullApi {
    private static WeatherFullApi instance;
    private WeatherApi apiJson;

    private WeatherFullApi(){
        apiJson = RetrofitFactory.getRetrofit(WeatherApi.BASE_URL_JSON).create(WeatherApi.class);
    }

    public static WeatherFullApi getInstance() {
        if(instance == null){
            instance = new WeatherFullApi();
        }
        return instance;
    }

    public WeatherApi getApiJson(){
        if(apiJson == null){
            new WeatherFullApi();
        }
        return apiJson;
    }
}

package com.example.manhtuan.myapplication.rest;

import java.nio.file.Path;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    public static Retrofit getRetrofit(String baseUrl){
        Retrofit retrofit;
        switch (baseUrl){
            case WeatherApi.BASE_URL_JSON:
                retrofit = new Retrofit.Builder()
                        .baseUrl(WeatherApi.BASE_URL_JSON)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                return retrofit;
        }
        return null;
    }
}

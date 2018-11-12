package com.example.manhtuan.myapplication.rest;

import com.example.manhtuan.myapplication.model.Json;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherApi {
    static final String BASE_URL_JSON = "https://api.darksky.net/forecast/";
    @GET("d319ab0a4143df360d6c978092c5b1d1/{latitude},{longtitude}")
    Call<Json> getJson(@Path("latitude") String latitude,@Path("longtitude") String longtitude);
}

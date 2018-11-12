package com.example.manhtuan.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Json {
    @SerializedName("currently")
    @Expose
    private Currently currently;

    @SerializedName("daily")
    @Expose
    private Daily daily;

    public Currently getCurrently() {
        return currently;
    }

    public void setCurrently(Currently currently) {
        this.currently = currently;
    }

    public Daily getDaily() {
        return daily;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }
}

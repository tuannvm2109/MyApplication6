package com.example.manhtuan.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DayData {
    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("visibility")
    @Expose
    private String visibility;

    @SerializedName("uvIndex")
    @Expose
    private String uvIndex;

    @SerializedName("temperatureHigh")
    @Expose
    private float temperature;

    @SerializedName("humidity")
    @Expose
    private float humidity;

    @SerializedName("windSpeed")
    @Expose
    private float windSpeed;

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(String uvIndex) {
        this.uvIndex = uvIndex;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }
}

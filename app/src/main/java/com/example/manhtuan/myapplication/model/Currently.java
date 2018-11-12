package com.example.manhtuan.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Currently {
    @SerializedName("summary")
    @Expose
    private String summary;

    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("temperature")
    @Expose
    private float temperature;

    @SerializedName("humidity")
    @Expose
    private float humidity;

    @SerializedName("visibility")
    @Expose
    private float visibility;

    @SerializedName("uvIndex")
    @Expose
    private float uvIndex;

    @SerializedName("windSpeed")
    @Expose
    private float windSpeed;

    @SerializedName("cloundCover")
    @Expose
    private float cloundCover;

    @SerializedName("pressure")
    @Expose
    private float pressure;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public float getVisibility() {
        return visibility;
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }

    public float getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(float uvIndex) {
        this.uvIndex = uvIndex;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getCloundCover() {
        return cloundCover;
    }

    public void setCloundCover(float cloundCover) {
        this.cloundCover = cloundCover;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }
}

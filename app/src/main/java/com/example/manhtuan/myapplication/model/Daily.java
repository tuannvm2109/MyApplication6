package com.example.manhtuan.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Daily {
    @SerializedName("summary")
    @Expose
    private String summary;

    @SerializedName("data")
    @Expose
    private List<DayData> dayDataList;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<DayData> getDayDataList() {
        return dayDataList;
    }

    public void setDayDataList(List<DayData> dayDataList) {
        this.dayDataList = dayDataList;
    }
}

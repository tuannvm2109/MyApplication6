package com.example.manhtuan.myapplication.customview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.manhtuan.myapplication.R;
import com.example.manhtuan.myapplication.model.DayData;
import com.github.pwittchen.weathericonview.WeatherIconView;

import java.util.ArrayList;
import java.util.Calendar;

public class SummaryDayView extends LinearLayout{
    private TextView mTextViewDay,mTextViewVisibility,mTextViewUV;
    private WeatherIconView mWeatherIconView;

    public SummaryDayView(Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.custom_summary_day_view,this);
        mTextViewDay = findViewById(R.id.textViewDaySummaryDayView);
        mTextViewUV = findViewById(R.id.textViewUVSummaryDayView);
        mTextViewVisibility = findViewById(R.id.textViewVisibilitySummaryDayView);
        mWeatherIconView = findViewById(R.id.weatherIconViewSummaryDayView);
    }
    public void setView(DayData dayData,String day){
        mTextViewVisibility.setText(dayData.getVisibility());
        mTextViewUV.setText(dayData.getUvIndex());
        mTextViewDay.setText(day);

        mWeatherIconView.setIconResource(getContext().getString(R.string.wi_day_sunny_overcast));
        mWeatherIconView.setIconSize(25);
        mWeatherIconView.setIconColor(Color.BLACK);
    }
}

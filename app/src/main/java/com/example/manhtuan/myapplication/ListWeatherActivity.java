package com.example.manhtuan.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manhtuan.myapplication.model.Currently;
import com.example.manhtuan.myapplication.model.DayData;
import com.example.manhtuan.myapplication.model.Json;
import com.example.manhtuan.myapplication.rest.RestCallBack;
import com.example.manhtuan.myapplication.rest.WeatherApi;
import com.example.manhtuan.myapplication.rest.WeatherFullApi;
import com.github.mikephil.charting.charts.LineChart;
import com.github.pwittchen.weathericonview.WeatherIconView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class ListWeatherActivity extends AppCompatActivity {
    private double mLatitude, mLongtitude;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_weather);
        mappingView();
        setData();
        setView();
        new LoadApi().execute();
    }

    private void setView() {
        recyclerView.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setData() {
        Intent intent = getIntent();
        if(intent == null){
            Toast.makeText(this, "error Intent", Toast.LENGTH_SHORT).show();
        }
        else{
            Bundle bundle = intent.getBundleExtra(MapsActivity.EXTRA_BUNDLE);
            if(bundle == null){
                Toast.makeText(this, "error Bundle", Toast.LENGTH_SHORT).show();
            }
            else {
                mLatitude = bundle.getDouble(MapsActivity.EXTRA_LATITUDE);
                mLongtitude = bundle.getDouble(MapsActivity.EXTRA_LONGTITUDE);
            }
        }
    }

    private void mappingView() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    class LoadApi extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            WeatherApi weatherApi = WeatherFullApi.getInstance().getApiJson();
            Call<Json> jsonCall = weatherApi.getJson(mLatitude +"",mLongtitude + "");
            RestCallBack.ResponseListener listener = new RestCallBack.ResponseListener() {
                @Override
                public void onSuccess(Object objT2) {
                    Json json = (Json) objT2;
                    Currently currently = json.getCurrently();
                    List<PropertiesDetail> propertiesDetailList = new ArrayList<>();
                    propertiesDetailList.add(new PropertiesDetail("Summary",currently.getSummary()));
                    propertiesDetailList.add(new PropertiesDetail("Icon",currently.getIcon()));
                    propertiesDetailList.add(new PropertiesDetail("Temperature",currently.getTemperature()+""));
                    propertiesDetailList.add(new PropertiesDetail("Humidity",currently.getHumidity()+""));
                    propertiesDetailList.add(new PropertiesDetail("Pressure",currently.getPressure()+""));
                    propertiesDetailList.add(new PropertiesDetail("Visibility",currently.getVisibility()+""));
                    propertiesDetailList.add(new PropertiesDetail("CloundCover",currently.getCloundCover()+""));
                    propertiesDetailList.add(new PropertiesDetail("WindSpeed",currently.getWindSpeed()+""));
                    propertiesDetailList.add(new PropertiesDetail("UvIndex",currently.getUvIndex()+""));


//                    Map<String,String> map = new LinkedHashMap<>();
//                    map.put("Summary",currently.getSummary());
//                    map.put("Icon",currently.getIcon());
//                    map.put("Temperature",currently.getTemperature()+"");
//                    map.put("Humidity",currently.getHumidity()+"");
//                    map.put("Pressure",currently.getPressure()+"");
//                    map.put("Visibility",currently.getVisibility()+"");
//                    map.put("CloundCover",currently.getCloundCover()+"");
//                    map.put("WindSpeed",currently.getWindSpeed()+"");
//                    map.put("UvIndex",currently.getUvIndex()+"");
                    RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(ListWeatherActivity.this,propertiesDetailList);
                    recyclerView.setAdapter(recyclerViewAdapter);
                }
            };

            RestCallBack<Json,Json> restCallBack = new RestCallBack<Json, Json>(listener) {
                @Override
                public Json getData(Json objT1) {
                    return objT1;
                }
            };
            jsonCall.enqueue(restCallBack);
            return null;
        }
    }
    class PropertiesDetail{
        private String properties;
        private String detail;

        public PropertiesDetail(String properties, String detail) {
            this.properties = properties;
            this.detail = detail;
        }

        public String getProperties() {
            return properties;
        }

        public void setProperties(String properties) {
            this.properties = properties;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }
}

package com.example.manhtuan.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manhtuan.myapplication.customview.SummaryDayView;
import com.example.manhtuan.myapplication.model.Currently;
import com.example.manhtuan.myapplication.model.DayData;
import com.example.manhtuan.myapplication.model.Json;
import com.example.manhtuan.myapplication.rest.RestCallBack;
import com.example.manhtuan.myapplication.rest.WeatherApi;
import com.example.manhtuan.myapplication.rest.WeatherFullApi;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.pwittchen.weathericonview.WeatherIconView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;

public class WeatherActivity extends AppCompatActivity {
    private static final String TEMPERATURE = "com.example.manhtuan.myapplication.temperature";
    private static final String HUMIDITY = "com.example.manhtuan.myapplication.humidity";
    private static final String WIND_SPEED = "com.example.manhtuan.myapplication.windspeed";


    private double mLatitude, mLongtitude;
    private LineChart mLineChartTemp;
    private TextView mTextViewLocation,mTextViewTemperature,mTextViewHumidity,mTextViewWindSpeed,mTextViewSummary;
    private List<DayData> mDayDataList;
    private WeatherIconView mWeatherIconView;
    private LinearLayout linearLayoutSummary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        mappingView();
        setData();
        setView();
        new LoadApi().execute();

        
    }


    class LoadApi extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            WeatherApi weatherApi = WeatherFullApi.getInstance().getApiJson();
            Call<Json> jsonCall = weatherApi.getJson(mLatitude +"",mLongtitude + "");
            RestCallBack.ResponseListener listener = new RestCallBack.ResponseListener() {
                @Override
                public void onSuccess(Object objT2) {
                    Json json = (Json) objT2;

//                    Log.d("asdf","uv" +json.getDaily().getDayDataList().get(1).getUvIndex());
//                    Log.d("asdf","visi" +json.getDaily().getDayDataList().get(1).getVisibility());
//                    Log.d("asdf","temp" +json.getDaily().getDayDataList().get(1).getTemperature());
//                    Log.d("asdf","icon" +json.getDaily().getDayDataList().get(1).getIcon());
                    setChart(json.getDaily().getDayDataList());
                    setCurrentWeather(json.getCurrently());
                    setSummaryLayout(json.getDaily().getDayDataList());

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


    private void setCurrentWeather(Currently currentWeather){
        mWeatherIconView.setIconResource(getString(R.string.wi_day_sunny_overcast));
        mWeatherIconView.setIconSize(80);
        mWeatherIconView.setIconColor(Color.BLACK);
        mTextViewSummary.setText(currentWeather.getSummary());
        DecimalFormat df = new DecimalFormat("#.0");
        mTextViewTemperature.setText("Temp : " + df.format((currentWeather.getTemperature()-32) * 5 /9)+ " C");
        mTextViewHumidity.setText("Humidity : " + (currentWeather.getHumidity()*100) + " %");
        mTextViewWindSpeed.setText("Wind Speed : " + currentWeather.getWindSpeed() + " km/h");
    }

    private void setSummaryLayout(List<DayData> dayDataList){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String dayString;
        for (int i = 0; i < 7; i++) {
            if(day > 8) day -= 7;
            if(day == 8) dayString = "CN";
            else dayString = "T" + day;
            day++;
            SummaryDayView summaryDayView = new SummaryDayView(this);
            summaryDayView.setView(dayDataList.get(i+1),dayString);
            summaryDayView.setWeightSum(1);
            linearLayoutSummary.addView(summaryDayView);
        }

    }

    private void setChart(List<DayData> dayDataList){
        List<ILineDataSet> lines = new ArrayList<> ();
        lines.add(getLineDataSet(dayDataList,TEMPERATURE));
        lines.add(getLineDataSet(dayDataList,HUMIDITY));
        lines.add(getLineDataSet(dayDataList,WIND_SPEED));
        LineData lineData = new LineData(lines);
        mLineChartTemp.setData(lineData);
        mLineChartTemp.invalidate();

    }

    private LineDataSet getLineDataSet (List<DayData> dayDataList, String type){
        LineDataSet lineDataSet = null;
        switch (type){
            case TEMPERATURE :
                List<Entry> temperatures = new ArrayList<>();
                for (int i = 1; i < 8; i++) {
                    temperatures.add(new Entry(i,(dayDataList.get(i).getTemperature()-32) * 5 /9));
                }
                lineDataSet = new LineDataSet(temperatures, "Temperature (C)");
                lineDataSet.setColor(Color.RED);
                lineDataSet.setCircleColor(Color.RED);
                break;
            case HUMIDITY :
                List<Entry> humiditys = new ArrayList<>();
                for (int i = 1; i < 8; i++) {
                    humiditys.add(new Entry(i,dayDataList.get(i).getHumidity() * 100));
                }
                lineDataSet = new LineDataSet(humiditys, "Humidity (%)");
                lineDataSet.setColor(Color.BLUE);
                lineDataSet.setCircleColor(Color.BLUE);
                break;
            case WIND_SPEED:
                List<Entry> windSpeeds = new ArrayList<>();
                for (int i = 1; i < 8; i++) {
                    windSpeeds.add(new Entry(i,dayDataList.get(i).getWindSpeed()));
                }
                lineDataSet = new LineDataSet(windSpeeds, "Wind Speed (km/h)");
                lineDataSet.setColor(Color.GREEN);
                lineDataSet.setCircleColor(Color.GREEN);
                break;
        }
        return lineDataSet;
    }

    private void setView() {
        Description description = new Description();
        description.setText("Weather chart of week");
        mLineChartTemp.setDescription(description);

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        final ArrayList<String> xLabel = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if(day > 8) day -= 7;

            if(day == 8) xLabel.add("CN");
            else xLabel.add("T" + day);
            day++;
        }
        XAxis xAxis = mLineChartTemp.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabel.get((int)value -1 );
            }
        });
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
                mTextViewLocation.setText(bundle.getString(MapsActivity.EXTRA_ADDRESS));
            }
        }
        mDayDataList = new ArrayList<>();
    }

    private void mappingView() {
        linearLayoutSummary = findViewById(R.id.linearLayoutSummaryWeek);
        mWeatherIconView = findViewById(R.id.weatherIconView);
        mTextViewLocation = findViewById(R.id.textViewLocation);
        mTextViewTemperature = findViewById(R.id.textViewTemperature);
        mTextViewHumidity = findViewById(R.id.textViewHumidity);
        mTextViewWindSpeed = findViewById(R.id.textViewWindSpeed);
        mTextViewSummary = findViewById(R.id.textViewSummary);
        mLineChartTemp = findViewById(R.id.lineChartTemp);
    }
}

package com.example.yazuz.weatherapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class Pop extends Activity {

    TextView theDate, todayTemp, todayWeatherHigh, todayWeatherLow, todayHumidity, todayWindSpeed, todayPressure;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.6), (int)(height*0.6));

        final String degree = getString(R.string.degree);
        final String pUnit = getString(R.string.pUnit);
        final String percent = getString(R.string.percent);
        final String windUnit = getString(R.string.windUnit);

        todayTemp = (TextView) findViewById(R.id.todayTemp);
        todayWeatherHigh = (TextView) findViewById(R.id.todayWeatherHigh);
        todayWeatherLow = (TextView) findViewById(R.id.todayWeatherLow);
        todayHumidity = (TextView) findViewById(R.id.todayHumidity);
        todayPressure = (TextView) findViewById(R.id.todayPressure);
        todayWindSpeed = (TextView) findViewById(R.id.todayWindSpeed);
        theDate = (TextView) findViewById(R.id.theDate);

        //Getting the data sent from the main activity
        Integer temp = getIntent().getIntExtra("todayTemp",0);
        Integer tempMax = getIntent().getIntExtra("today_tmax",0);
        Integer tempMin = getIntent().getIntExtra("today_tmin",0);
        Integer humidity = getIntent().getIntExtra("today_hum",0);
        Integer pressure = getIntent().getIntExtra("today_press",0);
        Double wind = getIntent().getDoubleExtra("today_wind",0);
        String date = getIntent().getStringExtra("dateToday");

        //Updating the fixed data to the live data collected from the main activity
        todayTemp.setText(String.valueOf(temp)+degree);
        todayWeatherHigh.setText(String.valueOf(tempMax)+degree);
        todayWeatherLow.setText(String.valueOf(tempMin)+degree);
        todayHumidity.setText(String.valueOf(humidity)+percent);
        todayPressure.setText(String.valueOf(pressure)+" "+ pUnit);
        todayWindSpeed.setText(String.valueOf(wind)+ " " + windUnit);
        theDate.setText(date);
    }
}

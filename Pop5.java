package com.example.yazuz.weatherapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class Pop5 extends Activity {

    TextView day4Date, day4Temp, day4WeatherHigh, day4WeatherLow, day4Humidity, day4WindSpeed, day4Pressure;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_window5);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.6), (int)(height*0.6));

        final String degree = getString(R.string.degree);
        final String pUnit = getString(R.string.pUnit);
        final String percent = getString(R.string.percent);
        final String windUnit = getString(R.string.windUnit);

        day4Temp = (TextView) findViewById(R.id.day4Temp);
        day4WeatherHigh = (TextView) findViewById(R.id.day4WeatherHigh);
        day4WeatherLow = (TextView) findViewById(R.id.day4WeatherLow);
        day4Humidity = (TextView) findViewById(R.id.day4Humidity);
        day4Pressure = (TextView) findViewById(R.id.day4Pressure);
        day4WindSpeed = (TextView) findViewById(R.id.day4WindSpeed);
        day4Date = (TextView) findViewById(R.id.day4Date);

        //Getting the data sent from the main activity
        Integer temp = getIntent().getIntExtra("day4Temp",0);
        Integer tempMax = getIntent().getIntExtra("day4TempMax",0);
        Integer tempMin = getIntent().getIntExtra("day4TempMin",0);
        Integer humidity = getIntent().getIntExtra("day4Hum",0);
        Integer pressure = getIntent().getIntExtra("day4Press",0);
        Double wind = getIntent().getDoubleExtra("day4Wind",0);
        String date = getIntent().getStringExtra("day4Date");

        //Updating the fixed data to the live data collected from the main activity
        day4Temp.setText(String.valueOf(temp)+degree);
        day4WeatherHigh.setText(String.valueOf(tempMax)+degree);
        day4WeatherLow.setText(String.valueOf(tempMin)+degree);
        day4Humidity.setText(String.valueOf(humidity)+percent);
        day4Pressure.setText(String.valueOf(pressure)+" "+ pUnit);
        day4WindSpeed.setText(String.valueOf(wind)+ " " + windUnit);
        day4Date.setText(date);
    }
}

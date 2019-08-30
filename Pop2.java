package com.example.yazuz.weatherapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class Pop2 extends Activity {

    TextView day1Date, day1Temp, day1WeatherHigh, day1WeatherLow, day1Humidity, day1WindSpeed, day1Pressure;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_window2);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.6), (int)(height*0.6));

        final String degree = getString(R.string.degree);
        final String pUnit = getString(R.string.pUnit);
        final String percent = getString(R.string.percent);
        final String windUnit = getString(R.string.windUnit);

        day1Temp = (TextView) findViewById(R.id.day1Temp);
        day1WeatherHigh = (TextView) findViewById(R.id.day1WeatherHigh);
        day1WeatherLow = (TextView) findViewById(R.id.day1WeatherLow);
        day1Humidity = (TextView) findViewById(R.id.day1Humidity);
        day1Pressure = (TextView) findViewById(R.id.day1Pressure);
        day1WindSpeed = (TextView) findViewById(R.id.day1WindSpeed);
        day1Date = (TextView) findViewById(R.id.day1Date);

        //Getting the data sent from the main activity
        Integer temp = getIntent().getIntExtra("day1Temp",0);
        Integer tempMax = getIntent().getIntExtra("day1TempMax",0);
        Integer tempMin = getIntent().getIntExtra("day1TempMin",0);
        Integer humidity = getIntent().getIntExtra("day1Hum",0);
        Integer pressure = getIntent().getIntExtra("day1Press",0);
        Double wind = getIntent().getDoubleExtra("day1Wind",0);
        String date = getIntent().getStringExtra("day1Date");

        //Updating the fixed data to the live data collected from the main activity
        day1Temp.setText(String.valueOf(temp)+degree);
        day1WeatherHigh.setText(String.valueOf(tempMax)+degree);
        day1WeatherLow.setText(String.valueOf(tempMin)+degree);
        day1Humidity.setText(String.valueOf(humidity)+percent);
        day1Pressure.setText(String.valueOf(pressure)+" "+ pUnit);
        day1WindSpeed.setText(String.valueOf(wind)+ " " + windUnit);
        day1Date.setText(date);
    }
}

package com.example.yazuz.weatherapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class Pop4 extends Activity {

    TextView day3Date, day3Temp, day3WeatherHigh, day3WeatherLow, day3Humidity, day3WindSpeed, day3Pressure;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_window4);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.6), (int)(height*0.6));

        final String degree = getString(R.string.degree);
        final String pUnit = getString(R.string.pUnit);
        final String percent = getString(R.string.percent);
        final String windUnit = getString(R.string.windUnit);

        day3Temp = (TextView) findViewById(R.id.day3Temp);
        day3WeatherHigh = (TextView) findViewById(R.id.day3WeatherHigh);
        day3WeatherLow = (TextView) findViewById(R.id.day3WeatherLow);
        day3Humidity = (TextView) findViewById(R.id.day3Humidity);
        day3Pressure = (TextView) findViewById(R.id.day3Pressure);
        day3WindSpeed = (TextView) findViewById(R.id.day3WindSpeed);
        day3Date = (TextView) findViewById(R.id.day3Date);

        //Getting the data sent from the main activity
        Integer temp = getIntent().getIntExtra("day3Temp",0);
        Integer tempMax = getIntent().getIntExtra("day3TempMax",0);
        Integer tempMin = getIntent().getIntExtra("day3TempMin",0);
        Integer humidity = getIntent().getIntExtra("day3Hum",0);
        Integer pressure = getIntent().getIntExtra("day3Press",0);
        Double wind = getIntent().getDoubleExtra("day3Wind",0);
        String date = getIntent().getStringExtra("day3Date");

        //Updating the fixed data to the live data collected from the main activity
        day3Temp.setText(String.valueOf(temp)+degree);
        day3WeatherHigh.setText(String.valueOf(tempMax)+degree);
        day3WeatherLow.setText(String.valueOf(tempMin)+degree);
        day3Humidity.setText(String.valueOf(humidity)+percent);
        day3Pressure.setText(String.valueOf(pressure)+" "+ pUnit);
        day3WindSpeed.setText(String.valueOf(wind)+ " " + windUnit);
        day3Date.setText(date);
    }
}

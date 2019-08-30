package com.example.yazuz.weatherapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class Pop3 extends Activity {

    TextView day2Date, day2Temp, day2WeatherHigh, day2WeatherLow, day2Humidity, day2WindSpeed, day2Pressure;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_window3);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.6), (int)(height*0.6));

        final String degree = getString(R.string.degree);
        final String pUnit = getString(R.string.pUnit);
        final String percent = getString(R.string.percent);
        final String windUnit = getString(R.string.windUnit);

        day2Temp = (TextView) findViewById(R.id.day2Temp);
        day2WeatherHigh = (TextView) findViewById(R.id.day2WeatherHigh);
        day2WeatherLow = (TextView) findViewById(R.id.day2WeatherLow);
        day2Humidity = (TextView) findViewById(R.id.day2Humidity);
        day2Pressure = (TextView) findViewById(R.id.day2Pressure);
        day2WindSpeed = (TextView) findViewById(R.id.day2WindSpeed);
        day2Date = (TextView) findViewById(R.id.day2Date);

        //Getting the data sent from the main activity
        Integer temp = getIntent().getIntExtra("day2Temp",0);
        Integer tempMax = getIntent().getIntExtra("day2TempMax",0);
        Integer tempMin = getIntent().getIntExtra("day2TempMin",0);
        Integer humidity = getIntent().getIntExtra("day2Hum",0);
        Integer pressure = getIntent().getIntExtra("day2Press",0);
        Double wind = getIntent().getDoubleExtra("day2Wind",0);
        String date = getIntent().getStringExtra("day2Date");

        //Updating the fixed data to the live data collected from the main activity
        day2Temp.setText(String.valueOf(temp)+degree);
        day2WeatherHigh.setText(String.valueOf(tempMax)+degree);
        day2WeatherLow.setText(String.valueOf(tempMin)+degree);
        day2Humidity.setText(String.valueOf(humidity)+percent);
        day2Pressure.setText(String.valueOf(pressure)+" "+ pUnit);
        day2WindSpeed.setText(String.valueOf(wind)+ " " + windUnit);
        day2Date.setText(date);
    }
}

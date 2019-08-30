package com.example.yazuz.weatherapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    public static Activity act;
    String current_location="Exeter";
    Button moreBtn;
    LinearLayout day1_layout, day2_layout, day3_layout, day4_layout;
    TextView todayTempMain, dateMain, day1TempMain, day1, day2TempMain, day2, day3TempMain, day3, day4TempMain, day4, location;
    ImageView todayIcon, day1Icon, day2Icon, day3Icon, day4Icon, refresh;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        act = this;

        try {
            openDatabase();
            createTable();
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        String loc = getIntent().getStringExtra("location"); //get the location chosen by the user
        if (loc != null){
            current_location = loc;
        } else {
            current_location = "Exeter"; //default location
        }

        //The popup windows for each day of the forecast
        final Intent popup1 = new Intent(getBaseContext(), Pop.class);
        final Intent popup2 = new Intent(getBaseContext(), Pop2.class);
        final Intent popup3 = new Intent(getBaseContext(), Pop3.class);
        final Intent popup4 = new Intent(getBaseContext(), Pop4.class);
        final Intent popup5 = new Intent(getBaseContext(), Pop5.class);

        moreBtn     = (Button) findViewById(R.id.buttonMore);
        refresh     = (ImageView) findViewById(R.id.refreshButton);
        day1_layout = (LinearLayout) findViewById(R.id.day1layout);
        day2_layout = (LinearLayout) findViewById(R.id.day2layout);
        day3_layout = (LinearLayout) findViewById(R.id.day3layout);
        day4_layout = (LinearLayout) findViewById(R.id.day4layout);

        todayTempMain = (TextView) findViewById(R.id.todayTempMain);
        day1TempMain  = (TextView) findViewById(R.id.day1TempMain);
        day2TempMain  = (TextView) findViewById(R.id.day2TempMain);
        day3TempMain  = (TextView) findViewById(R.id.day3TempMain);
        day4TempMain  = (TextView) findViewById(R.id.day4TempMain);
        location      = (TextView) findViewById(R.id.location);

        dateMain  = (TextView) findViewById(R.id.dateMain);
        day1      = (TextView) findViewById(R.id.day1);
        day2      = (TextView) findViewById(R.id.day2);
        day3      = (TextView) findViewById(R.id.day3);
        day4      = (TextView) findViewById(R.id.day4);

        todayIcon = (ImageView) findViewById(R.id.todayIcon);
        day1Icon  = (ImageView) findViewById(R.id.day1Icon);
        day2Icon  = (ImageView) findViewById(R.id.day2Icon);
        day3Icon  = (ImageView) findViewById(R.id.day3Icon);
        day4Icon  = (ImageView) findViewById(R.id.day4Icon);

        String url = "http://api.openweathermap.org/data/2.5/forecast?q="+current_location+"&appid=9ffaf45665068daa27fb13797a432f9f&units=metric";

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray main_array = response.getJSONArray("list"); //list of all the days
                    JSONObject city = response.getJSONObject("city"); //HAS NAME OF CITY
                    String cityName = String.valueOf(city.getString("name")); //name of city

                    location.setText(cityName);

                    //Functions to update the weather data for each day.
                    // ********* TODAY *********
                    weatherToday(popup1, main_array);

                    // ********* DAY 1 *********
                    day1Weather(popup2,main_array);

                    // ********* DAY 2 *********
                    day2Weather(popup3,main_array);

                    // ********* DAY 3 *********
                    day3Weather(popup4,main_array);

                    // ********* DAY 4 *********
                    day4Weather(popup5,main_array);

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);

        refresh.setOnTouchListener( new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN: //refreshes the activity for new data
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        break;
                }
                return true;
            }
        });

        moreBtn.setOnTouchListener( new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        moreBtn.setTextColor(Color.parseColor("#33595656"));
                        break;
                    case MotionEvent.ACTION_UP: //opens up a popup view with more information about today's weather

                        //set color back to default
                        moreBtn.setTextColor(Color.parseColor("#212121"));
                        startActivity(popup1);
                        break;
                }
                return true;
            }
        });

        day1_layout.setOnTouchListener( new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        day1_layout.setBackgroundColor(Color.parseColor("#33FFFFFF"));
                        break;
                    case MotionEvent.ACTION_UP: //opens a popup view with more information of tomorrows weather

                        //set color back to default
                        day1_layout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        startActivity(popup2);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        day1_layout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }
                return true;
            }
        });

        day2_layout.setOnTouchListener( new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        day2_layout.setBackgroundColor(Color.parseColor("#33FFFFFF"));
                        break;
                    case MotionEvent.ACTION_UP: //detailed weather forecast in a popup window

                        //set color back to default
                        day2_layout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        startActivity(popup3);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        day2_layout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }
                return true;
            }
        });

        day3_layout.setOnTouchListener( new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        day3_layout.setBackgroundColor(Color.parseColor("#33FFFFFF"));
                        break;
                    case MotionEvent.ACTION_UP: //detailed weather forecast in a popup window

                        //set color back to default
                        day3_layout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        startActivity(popup4);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        day3_layout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }
                return true;
            }
        });

        day4_layout.setOnTouchListener( new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        day4_layout.setBackgroundColor(Color.parseColor("#33FFFFFF"));
                        break;
                    case MotionEvent.ACTION_UP: //detailed weather forecast in a popup window

                        //set color back to default
                        day4_layout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        startActivity(popup5);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        day4_layout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.changeLocation:
                Intent loc = new Intent(getBaseContext(), Location.class);
                startActivity(loc); //start the activity to change the location based on user input
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("SetTextI18n")
    public void weatherToday(Intent popup, JSONArray main_array) throws JSONException {
        final String degree = getString(R.string.degree);

        Calendar cal = Calendar.getInstance();
        String dayLongName = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()); //The date today
        int timeOfDay = cal.get(Calendar.HOUR_OF_DAY); //The current time

        JSONObject today = main_array.getJSONObject(0); //DAY TODAY

        JSONObject main = today.getJSONObject("main"); // HAS TEMP, MIN, MAX, PRESSURE, and HUMIDITY
        JSONArray weatherList = today.getJSONArray("weather"); //HAS DESCRIPTION OF WEATHER
        JSONObject winds = today.getJSONObject("wind"); // HAS WIND INFORMATION

        JSONObject description = weatherList.getJSONObject(0);
        String iconDesc = String.valueOf(description.getString("main")); //DESCRIPTION of weather

        String temp = String.valueOf(main.getDouble("temp")); //temperature
        String tempMin = String.valueOf(main.getDouble("temp_min")); //HIGH temperature
        String tempMax = String.valueOf(main.getDouble("temp_max")); //LOW temperature
        String press = String.valueOf(main.getDouble("pressure")); //PRESSURE
        String humid = String.valueOf(main.getInt("humidity")); //HUMIDITY
        String windSpeed = String.valueOf(winds.getDouble("speed")); //WIND SPEED

        //Converting the strings to doubles and integers
        Double temp_int = Double.parseDouble(temp);
        Double tempMin_int = Double.parseDouble(tempMin);
        Double tempMax_int = Double.parseDouble(tempMax);
        Double press_int = Double.parseDouble(press);
        Double wind_int = Double.parseDouble(windSpeed);
        Integer humid_int = Integer.parseInt(humid);

        //Rounding the values so that they are whole numbers
        long t_i = Math.round(temp_int);
        long tmn_i = Math.round(tempMin_int);
        long tmx_i = Math.round(tempMax_int);
        long p_i = Math.round(press_int);

        int t = (int) t_i;
        int tmn = (int) tmn_i;
        int tmx = (int) tmx_i;
        int p = (int) p_i;

        //updating the fixed values to live data
        todayTempMain.setText(String.valueOf(t)+degree);
        dateMain.setText(dayLongName);
        popup.putExtra("dateToday", dayLongName);
        popup.putExtra("todayTemp", t);
        popup.putExtra("today_tmax", tmx);
        popup.putExtra("today_tmin", tmn);
        popup.putExtra("today_press", p);
        popup.putExtra("today_wind", wind_int);
        popup.putExtra("today_hum", humid_int);

        if (Objects.equals(iconDesc, "Clear") && (timeOfDay >=17 && timeOfDay <= 27)) {
            todayIcon.setImageResource(R.drawable.night);
        } else if (Objects.equals(iconDesc, "Clear")) {
            todayIcon.setImageResource(R.drawable.sunny);
        } else if (Objects.equals(iconDesc, "Rain")) {
            todayIcon.setImageResource(R.drawable.rainy);
        } else if (Objects.equals(iconDesc, "Snow")) {
            todayIcon.setImageResource(R.drawable.snowy);
        } else if (Objects.equals(iconDesc, "Clouds")){
            todayIcon.setImageResource(R.drawable.cloudy);
        }
    }

    @SuppressLint("SetTextI18n")
    public void day1Weather(Intent popup, JSONArray main_array) throws JSONException {
        final String degree = getString(R.string.degree);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_WEEK,1);
        String dayLongName = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

        JSONObject day = main_array.getJSONObject(1);

        JSONObject main = day.getJSONObject("main"); // HAS TEMP,min and max, PRESSURE, HUMIDITY
        JSONArray weatherList = day.getJSONArray("weather"); //HAS CLEAR, RAINY...
        JSONObject winds = day.getJSONObject("wind");

        JSONObject description = weatherList.getJSONObject(0);
        String iconDesc = String.valueOf(description.getString("main"));

        String temp = String.valueOf(main.getDouble("temp"));
        String tempMin = String.valueOf(main.getDouble("temp_min"));
        String tempMax = String.valueOf(main.getDouble("temp_max"));
        String press = String.valueOf(main.getDouble("pressure"));
        String humid = String.valueOf(main.getInt("humidity"));
        String windSpeed = String.valueOf(winds.getDouble("speed"));

        Double temp_int = Double.parseDouble(temp);
        Double tempMin_int = Double.parseDouble(tempMin);
        Double tempMax_int = Double.parseDouble(tempMax);
        Double press_int = Double.parseDouble(press);
        Double wind_int = Double.parseDouble(windSpeed);
        Integer humid_int = Integer.parseInt(humid);


        long t_i = Math.round(temp_int);
        long tmn_i = Math.round(tempMin_int);
        long tmx_i = Math.round(tempMax_int);
        long p_i = Math.round(press_int);

        int t = (int) t_i;
        int tmn = (int) tmn_i;
        int tmx = (int) tmx_i;
        int p = (int) p_i;

        day1TempMain.setText(String.valueOf(t)+degree);
        day1.setText(dayLongName);
        popup.putExtra("day1Date", dayLongName);
        popup.putExtra("day1Temp", t);
        popup.putExtra("day1TempMax", tmx);
        popup.putExtra("day1TempMin", tmn);
        popup.putExtra("day1Press", p);
        popup.putExtra("day1Wind", wind_int);
        popup.putExtra("day1Hum", humid_int);

        if (Objects.equals(iconDesc, "Clear")) {
            day1Icon.setImageResource(R.drawable.sunny);
        } else if (Objects.equals(iconDesc, "Clouds")) {
            day1Icon.setImageResource(R.drawable.cloudy);
        } else if (Objects.equals(iconDesc, "Rain")) {
            day1Icon.setImageResource(R.drawable.rainy);
        } else if (Objects.equals(iconDesc, "Snow")) {
            day1Icon.setImageResource(R.drawable.snowy);
        }
    }

    @SuppressLint("SetTextI18n")
    public void day2Weather(Intent popup, JSONArray main_array) throws JSONException {
        final String degree = getString(R.string.degree);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_WEEK,2);
        String dayLongName = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

        JSONObject day = main_array.getJSONObject(2);

        JSONObject main = day.getJSONObject("main"); // HAS TEMP,min and max, PRESSURE, HUMIDITY
        JSONArray weatherList = day.getJSONArray("weather"); //HAS CLEAR, RAINY...
        JSONObject winds = day.getJSONObject("wind");

        JSONObject description = weatherList.getJSONObject(0);
        String iconDesc = String.valueOf(description.getString("main"));

        String temp = String.valueOf(main.getDouble("temp"));
        String tempMin = String.valueOf(main.getDouble("temp_min"));
        String tempMax = String.valueOf(main.getDouble("temp_max"));
        String press = String.valueOf(main.getDouble("pressure"));
        String humid = String.valueOf(main.getInt("humidity"));
        String windSpeed = String.valueOf(winds.getDouble("speed"));

        Double temp_int = Double.parseDouble(temp);
        Double tempMin_int = Double.parseDouble(tempMin);
        Double tempMax_int = Double.parseDouble(tempMax);
        Double press_int = Double.parseDouble(press);
        Double wind_int = Double.parseDouble(windSpeed);
        Integer humid_int = Integer.parseInt(humid);


        long t_i = Math.round(temp_int);
        long tmn_i = Math.round(tempMin_int);
        long tmx_i = Math.round(tempMax_int);
        long p_i = Math.round(press_int);

        int t = (int) t_i;
        int tmn = (int) tmn_i;
        int tmx = (int) tmx_i;
        int p = (int) p_i;

        day2TempMain.setText(String.valueOf(t)+degree);
        day2.setText(dayLongName);
        popup.putExtra("day2Date", dayLongName);
        popup.putExtra("day2Temp", t);
        popup.putExtra("day2TempMax", tmx);
        popup.putExtra("day2TempMin", tmn);
        popup.putExtra("day2Press", p);
        popup.putExtra("day2Wind", wind_int);
        popup.putExtra("day2Hum", humid_int);

        if (Objects.equals(iconDesc, "Clear")) {
            day2Icon.setImageResource(R.drawable.sunny);
        } else if (Objects.equals(iconDesc, "Clouds")) {
            day2Icon.setImageResource(R.drawable.cloudy);
        } else if (Objects.equals(iconDesc, "Rain")) {
            day2Icon.setImageResource(R.drawable.rainy);
        } else if (Objects.equals(iconDesc, "Snow")) {
            day2Icon.setImageResource(R.drawable.snowy);
        }
    }

    @SuppressLint("SetTextI18n")
    public void day3Weather(Intent popup, JSONArray main_array) throws JSONException {
        final String degree = getString(R.string.degree);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_WEEK,3);
        String dayLongName = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

        JSONObject day = main_array.getJSONObject(3);

        JSONObject main = day.getJSONObject("main"); // HAS TEMP,min and max, PRESSURE, HUMIDITY
        JSONArray weatherList = day.getJSONArray("weather"); //HAS CLEAR, RAINY...
        JSONObject winds = day.getJSONObject("wind");

        JSONObject description = weatherList.getJSONObject(0);
        String iconDesc = String.valueOf(description.getString("main"));

        String temp = String.valueOf(main.getDouble("temp"));
        String tempMin = String.valueOf(main.getDouble("temp_min"));
        String tempMax = String.valueOf(main.getDouble("temp_max"));
        String press = String.valueOf(main.getDouble("pressure"));
        String humid = String.valueOf(main.getInt("humidity"));
        String windSpeed = String.valueOf(winds.getDouble("speed"));

        Double temp_int = Double.parseDouble(temp);
        Double tempMin_int = Double.parseDouble(tempMin);
        Double tempMax_int = Double.parseDouble(tempMax);
        Double press_int = Double.parseDouble(press);
        Double wind_int = Double.parseDouble(windSpeed);
        Integer humid_int = Integer.parseInt(humid);


        long t_i = Math.round(temp_int);
        long tmn_i = Math.round(tempMin_int);
        long tmx_i = Math.round(tempMax_int);
        long p_i = Math.round(press_int);

        int t = (int) t_i;
        int tmn = (int) tmn_i;
        int tmx = (int) tmx_i;
        int p = (int) p_i;

        day3TempMain.setText(String.valueOf(t)+degree);
        day3.setText(dayLongName);
        popup.putExtra("day3Date", dayLongName);
        popup.putExtra("day3Temp", t);
        popup.putExtra("day3TempMax", tmx);
        popup.putExtra("day3TempMin", tmn);
        popup.putExtra("day3Press", p);
        popup.putExtra("day3Wind", wind_int);
        popup.putExtra("day3Hum", humid_int);

        if (Objects.equals(iconDesc, "Clear")) {
            day3Icon.setImageResource(R.drawable.sunny);
        } else if (Objects.equals(iconDesc, "Clouds")) {
            day3Icon.setImageResource(R.drawable.cloudy);
        } else if (Objects.equals(iconDesc, "Rain")) {
            day3Icon.setImageResource(R.drawable.rainy);
        } else if (Objects.equals(iconDesc, "Snow")) {
            day3Icon.setImageResource(R.drawable.snowy);
        }
    }

    @SuppressLint("SetTextI18n")
    public void day4Weather(Intent popup, JSONArray main_array) throws JSONException {
        final String degree = getString(R.string.degree);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_WEEK,4);
        String dayLongName = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

        JSONObject day = main_array.getJSONObject(4);

        JSONObject main = day.getJSONObject("main"); // HAS TEMP,min and max, PRESSURE, HUMIDITY
        JSONArray weatherList = day.getJSONArray("weather"); //HAS CLEAR, RAINY...
        JSONObject winds = day.getJSONObject("wind");

        JSONObject description = weatherList.getJSONObject(0);
        String iconDesc = String.valueOf(description.getString("main"));

        String temp = String.valueOf(main.getDouble("temp"));
        String tempMin = String.valueOf(main.getDouble("temp_min"));
        String tempMax = String.valueOf(main.getDouble("temp_max"));
        String press = String.valueOf(main.getDouble("pressure"));
        String humid = String.valueOf(main.getInt("humidity"));
        String windSpeed = String.valueOf(winds.getDouble("speed"));

        Double temp_int = Double.parseDouble(temp);
        Double tempMin_int = Double.parseDouble(tempMin);
        Double tempMax_int = Double.parseDouble(tempMax);
        Double press_int = Double.parseDouble(press);
        Double wind_int = Double.parseDouble(windSpeed);
        Integer humid_int = Integer.parseInt(humid);


        long t_i = Math.round(temp_int);
        long tmn_i = Math.round(tempMin_int);
        long tmx_i = Math.round(tempMax_int);
        long p_i = Math.round(press_int);

        int t = (int) t_i;
        int tmn = (int) tmn_i;
        int tmx = (int) tmx_i;
        int p = (int) p_i;

        day4TempMain.setText(String.valueOf(t)+degree);
        day4.setText(dayLongName);
        popup.putExtra("day4Date", dayLongName);
        popup.putExtra("day4Temp", t);
        popup.putExtra("day4TempMax", tmx);
        popup.putExtra("day4TempMin", tmn);
        popup.putExtra("day4Press", p);
        popup.putExtra("day4Wind", wind_int);
        popup.putExtra("day4Hum", humid_int);

        if (Objects.equals(iconDesc, "Clear")) {
            day4Icon.setImageResource(R.drawable.sunny);
        } else if (Objects.equals(iconDesc, "Clouds")) {
            day4Icon.setImageResource(R.drawable.cloudy);
        } else if (Objects.equals(iconDesc, "Rain")) {
            day4Icon.setImageResource(R.drawable.rainy);
        } else if (Objects.equals(iconDesc, "Snow")) {
            day4Icon.setImageResource(R.drawable.snowy);
        }
    }

    @SuppressLint("SdCardPath")
    private void openDatabase() {
        try {
            db = SQLiteDatabase.openDatabase(
                    "/data/data/com.example.yazuz.weatherapp/myDB", null, SQLiteDatabase.CREATE_IF_NECESSARY);
            //Toast.makeText(this, "DB was opened!", Toast.LENGTH_SHORT).show();
        } catch (SQLiteException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void createTable(){
        db.beginTransaction();
        try {
            db.execSQL("create table weatherData ("
                    + " day integer PRIMARY KEY autoincrement, "
                    + " weather text, "
                    + " date text, "
                    + " location text ); ");
            //commit your changes
            db.setTransactionSuccessful();

            Toast.makeText(this, "Table was created",Toast.LENGTH_SHORT).show();
        } catch (SQLException e1) {

        }
        finally {
            //finish transaction processing
            db.endTransaction();
        }
    }
}

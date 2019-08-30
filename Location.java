package com.example.yazuz.weatherapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Location extends Activity {
    String locName;
    EditText locInput;
    Button close, submit;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.location_window);

        locInput = (EditText) findViewById(R.id.cityInput);
        close = (Button) findViewById(R.id.closeBtn);
        submit = (Button) findViewById(R.id.citySubmit);

        submit.setOnTouchListener( new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        submit.setTextColor(Color.parseColor("#ff4081"));
                        break;
                    case MotionEvent.ACTION_UP: // Changes the location to the one entered by the user and opens up the main activity
                        Intent main = new Intent(getBaseContext(), MainActivity.class);
                        //set color back to default
                        submit.setTextColor(Color.parseColor("#FFFFFFFF"));
                        if (locInput.length() != 0) {
                            locName = locInput.getText().toString();
                            main.putExtra("location", locName);
                            MainActivity.act.finish(); // to prevent going back to previous location
                            startActivity(main);
                            Toast.makeText(Location.this, "Location has been change to " + locName, Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(Location.this, "Field cannot be empty! ", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                return true;
            }
        });

        close.setOnTouchListener( new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        close.setTextColor(Color.parseColor("#FFFFFFFF"));
                        break;
                    case MotionEvent.ACTION_UP: // goes back to the main activity if user decides not to change location

                        //set color back to default
                        close.setTextColor(Color.parseColor("#ff4081"));
                        finish();
                        break;
                }
                return true;
            }
        });
    }
}

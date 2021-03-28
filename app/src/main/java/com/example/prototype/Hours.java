package com.example.prototype;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Hours extends AppCompatActivity {

    CalendarView calendar;
    TextView date;
    TextView hours;
    Button giveaway;
    String hours_obj = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hours);
        date = findViewById(R.id.date);
        hours = findViewById(R.id.hours);
        calendar = findViewById(R.id.calendar);
        giveaway = findViewById(R.id.hours_button);

        calendar.setDate(System.currentTimeMillis(),false,true);
        calendar.setFirstDayOfWeek(2);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Add 1 in month because month index is start with 0
                String Date = dayOfMonth + "-" + (month + 1) + "-" + year;
                date.setText(Date);
                hours.setText("");

                Calendar c = Calendar.getInstance();
                c.set(year, month, dayOfMonth);
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                Date start = c.getTime();
                TimeZone tz = TimeZone.getTimeZone("UTC");
                @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
                df.setTimeZone(tz);
                String start_fmt = df.format(start);
                System.out.println(start_fmt);

                c.add(Calendar.DAY_OF_MONTH, 1);
                Date end = c.getTime();
                String end_fmt = df.format(end);
                System.out.println(end_fmt);

                ParseUser currentUser = ParseUser.getCurrentUser();
                String username = currentUser.getString("username");
                Log.d("CURRENT_USER", username);
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Shifts");
                query.whereEqualTo("user", username);
                query.whereGreaterThanOrEqualTo("start", start);
                query.whereLessThan("start", end);
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            Log.d("query", "Query success");
                            Date shift_start = object.getDate("start");
                            int hour_start = (int)(shift_start.getTime() % 86400000) / 3600000;
                            Log.d("START", String.valueOf(hour_start));
                            Date shift_end = object.getDate("end");
                            int hour_end = (int)(shift_end.getTime() % 86400000) / 3600000;
                            Log.d("END", String.valueOf(hour_end));
                            String shift = hour_start + " - " + hour_end;
                            Log.d("hours", shift);
                            hours.setText(shift);

                            hours_obj = object.getObjectId();
                            Log.d("OBJECT", hours_obj);
                        } else {
                            Log.d("query", "Query failed.");
                        }
                    }
                });
            }
        });

        giveaway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hours_obj != null) {
                    Log.d("OBJECT", hours_obj);
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Shifts");
                    query.getInBackground(hours_obj, new GetCallback<ParseObject>() {
                        public void done(ParseObject hours, ParseException e) {
                            if (e == null) {
                                hours.put("giveaway", true);
                                hours.saveInBackground();
                                Snackbar.make(v, "Shift made available to others.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                            else {
                                Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_LONG).show();

                            }
                        }
                    });
                }
                else {
                    Log.d("OBJECT", "Object is null");
                }
            }
        });

    }

    public void openAvailableHours(View view) {
        Intent intent = new Intent(this, AvailableHours.class);
        startActivity(intent);
    }
}
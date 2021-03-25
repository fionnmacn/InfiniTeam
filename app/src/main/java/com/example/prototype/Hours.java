package com.example.prototype;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Date;

public class Hours extends AppCompatActivity {

    CalendarView calendar;
    TextView date;
    TextView hours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hours);
        date = findViewById(R.id.date);
        hours = findViewById(R.id.hours);
        calendar = findViewById(R.id.calendar);

        calendar.setDate(System.currentTimeMillis(),false,true);
        calendar.setFirstDayOfWeek(2);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Add 1 in month because month index is start with 0
                long workDate;
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
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
                df.setTimeZone(tz);
                String start_fmt = df.format(start);
                System.out.println(start_fmt);

                c.add(Calendar.DAY_OF_MONTH, 1);
                Date end = c.getTime();
                String end_fmt = df.format(end);
                System.out.println(end_fmt);

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Shifts");
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
                        } else {
                            Log.d("query", "Query failed.");
                        }
                    }
                });
            }
        });
    }

    public void openAvailableHours(View view) {
        Intent intent = new Intent(this, AvailableHours.class);
        startActivity(intent);
    }
}
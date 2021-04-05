package com.example.InfiniTeam.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.InfiniTeam.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AvailableHours extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_hours);
        ListView listview = findViewById(R.id.list);

        Date currentDate = new Date();
        Log.d("CURRENT_TIME", String.valueOf(currentDate));

        ArrayList<HashMap<String, String>> hoursList = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Shifts");
        query.whereEqualTo("giveaway", true);
        query.whereGreaterThanOrEqualTo("start", currentDate);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> availableList, ParseException e) {
                for (int i = 0; i < availableList.size(); i++) {
                    HashMap<String, String> hours = new HashMap<>();
                    ParseObject object = availableList.get(i);
                    Log.d("OBJECT", object.toString());

                    SimpleDateFormat spf = new SimpleDateFormat("EEEE dd MMM, hh:mm aaa");
                    Date shift_start = object.getDate("start");
                    String shift_start_spf = spf.format(shift_start);
                    Date shift_end = object.getDate("end");
                    String shift_end_spf = spf.format(shift_end);

                    String id = object.getObjectId();

                    hours.put("objectId", id);
                    hours.put("start", shift_start_spf);
                    hours.put("end", shift_end_spf);
                    hoursList.add(hours);
                }
                Log.d("LIST", String.valueOf(hoursList));

                ListAdapter simpleAdapter = new SimpleAdapter(AvailableHours.this,
                        hoursList,
                        R.layout.hours_list,
                        new String[]{"start", "end", "objectId"},
                        new int[]{R.id.start, R.id.end, R.id.objectId});
                listview.setAdapter(simpleAdapter);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> selected_shift = (HashMap<String, String>) parent.getItemAtPosition(position);
                String objectId_clicked = selected_shift.get("objectId");
                Log.d("OBJECT_ID", objectId_clicked);
                String start_clicked = selected_shift.get("start");
                String end_clicked = selected_shift.get("end");

                Intent intent = new Intent(AvailableHours.this, SelectHours.class);
                intent.putExtra("objectId", objectId_clicked);
                intent.putExtra("start", start_clicked);
                intent.putExtra("end", end_clicked);

                startActivity(intent);
                finish();
            }
        });
    }
}
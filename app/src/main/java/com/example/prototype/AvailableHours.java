package com.example.prototype;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONObject;

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

        ArrayList<HashMap<String, String>> hoursList = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Shifts");
        query.whereEqualTo("giveaway", true);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> availableList, ParseException e) {
                for (int i = 0; i < availableList.size(); i++) {
                    HashMap<String, String> hours = new HashMap<>();
                    ParseObject object = availableList.get(i);
                    Log.d("OBJECT", object.toString());
                    Date shift_start = object.getDate("start");
                    Date shift_end = object.getDate("end");
                    String user = object.getString("user");
                    hours.put("user", user);
                    hours.put("start", shift_start.toString());
                    hours.put("end", shift_end.toString());
                    hoursList.add(hours);
                }
                Log.d("LIST", String.valueOf(hoursList));

                ListAdapter simpleAdapter = new SimpleAdapter(AvailableHours.this,
                        hoursList,
                        R.layout.hours_list,
                        new String[]{"start", "end"},
                        new int[]{R.id.start, R.id.end});
                listview.setAdapter(simpleAdapter);
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> selected_shift = (HashMap<String, String>) parent.getItemAtPosition(position);
//                String user = selected_shift.get("user");
                String start = selected_shift.get("start");
                String end = selected_shift.get("end");

                Intent intent = new Intent(AvailableHours.this, SelectHours.class);
//                intent.putExtra("user", user);
                intent.putExtra("start", start);
                intent.putExtra("end", end);
                startActivity(intent);
                finish();
            }
        });
    }
}
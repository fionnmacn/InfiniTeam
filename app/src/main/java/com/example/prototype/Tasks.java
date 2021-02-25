package com.example.prototype;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tasks extends AppCompatActivity {

    private final String TAG = Tasks.class.getSimpleName();
    private ListView lv;
    private List tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        tasks = new ArrayList<>();
        lv = (ListView) findViewById(R.id.taskList);
    }

    private class GetTasks extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(Tasks.this,"Downloading",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Tasks");
            query.whereEqualTo("completed", "false");
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> taskList, ParseException e) {
                    if (e == null) {
                        Log.d("score", "Retrieved " + taskList.size() + " scores");
                    } else {
                        Log.d("score", "Error: " + e.getMessage());
                    }
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(Tasks.this,
                    tasks,
                    R.layout.task_list,
                    new String[]{ "email","mobile"},
                    new int[]{R.id.email, R.id.mobile});
            lv.setAdapter(adapter);
        }
    }
}

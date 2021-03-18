package com.example.prototype;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseObject;
import com.parse.ParseQuery;


import java.util.ArrayList;
import java.util.HashMap;
public class Tasks extends AppCompatActivity {

    private ListView lv;
    private ArrayList<HashMap<String, Object>> list;
    String[] from = { "description","time"};
    int[] to = {R.id.firstLine, R.id.secondLine};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        lv = findViewById(R.id.list);
//        new GetTasks().execute();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tasks");
        query.whereEqualTo("completed", false);
        query.findInBackground((taskList, e) -> {
            if (e == null) {
                Log.d("tasks", "Retrieved " + taskList.size() + " tasks");
                Log.d("taskList", taskList.toString());

                if(taskList.size()>0 ){
                    for (ParseObject task : taskList) {
                        String taskDesc = task.getString("description");
                        String taskTime = task.getString("createdAt");
                        //Log.d("task", taskDesc);

                        HashMap<String, Object> map = new HashMap<>();
                        // Data entry in HashMap
                        map.put("description", taskDesc);
                        map.put("time", taskTime);
                        // adding the HashMap to the ArrayList
                        list.add(map);
                    }
                }
            } else {
                Log.d("tasks", "Error: " + e.getMessage());
            }
        });

        ListAdapter adapter = new SimpleAdapter(Tasks.this,
                list,
                R.layout.task_list,
                from,
                to);
        lv.setAdapter(adapter);
    }









//    private class GetTasks extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            Toast.makeText(Tasks.this,"Downloading",Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        protected void doInBackground(Void... arg0) {
//
//            ParseQuery<ParseObject> query = ParseQuery.getQuery("Tasks");
//            query.whereEqualTo("completed", false);
//            query.findInBackground((taskList, e) -> {
//                if (e == null) {
//                    Log.d("tasks", "Retrieved " + taskList.size() + " tasks");
//                    Log.d("taskList", taskList.toString());
//
//                    if(taskList.size()>0 ){
//                        for (ParseObject task : taskList) {
//                            String taskDesc = task.getString("description");
//                            String taskTime = task.getString("createdAt");
//                            //Log.d("task", taskDesc);
//
//                            HashMap<String, Object> map = new HashMap<>();
//                            // Data entry in HashMap
//                            map.put("description", taskDesc);
//                            map.put("time", taskTime);
//                            // adding the HashMap to the ArrayList
//                            list.add(map);
//                        }
//                    }
//                } else {
//                    Log.d("tasks", "Error: " + e.getMessage());
//                }
//            });
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(void result) {
//            super.onPostExecute(result);
//            ListAdapter adapter = new SimpleAdapter(Tasks.this,
//                    list,
//                    R.layout.task_list,
//                    from,
//                    to);
//            lv.setAdapter(adapter);
//        }
//    }
}

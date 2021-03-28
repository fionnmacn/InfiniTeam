package com.example.prototype;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Tasks extends AppCompatActivity {

    TextView selectedTask;
    private ArrayList<HashMap<String, String>> taskList;
    Button complete;
    Button decline;
    String currentTaskId = null;
    Boolean activeTask = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        ListView listview = findViewById(R.id.list);
        selectedTask = findViewById(R.id.currentTask);
        complete = findViewById(R.id.complete);
        decline = findViewById(R.id.decline);

        ParseUser currentUser = ParseUser.getCurrentUser();
        String user = currentUser.getUsername();
        Log.d("USER", user);

        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Tasks");
        query1.whereEqualTo("assignedTo", user);
        query1.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    String description = object.getString("description");
                    currentTaskId = object.getObjectId();
                    Log.d("CURRENT_TASK", description);
                    selectedTask.setText(description);
                    activeTask = true;
                    complete.setVisibility(View.VISIBLE);
                    decline.setVisibility(View.VISIBLE);
                } else {
                    Log.d("CURRENT_TASK", "Failed to retrieve the current task");
                    activeTask = false;
                    complete.setVisibility(View.GONE);
                    decline.setVisibility(View.GONE);
                }
            }
        });

        ArrayList<HashMap<String, String>> taskList = new ArrayList<>();
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Tasks");
        query2.whereEqualTo("completed", false);
        query2.orderByDescending("createdAt");
        query2.whereEqualTo("assignedTo", null);
        query2.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objectList, ParseException e) {
                if (e == null) {
                    Log.d("OBJECT_LIST", "Retrieved " + objectList.size() + " tasks");

                    for (int i = 0; i < objectList.size(); i++) {
                        HashMap<String, String> task = new HashMap<>();
                        ParseObject object = objectList.get(i);
                        Log.d("OBJECT", object.toString());

                        String id = object.getObjectId();
                        Log.d("OBJ_ID", id);
                        String description = object.getString("description");
                        Log.d("OBJ_DESC", description);
                        Date created = object.getCreatedAt();

                        task.put("objectId", id);
                        task.put("description", description);
                        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, hh:mm aaa");
                        String created_sdf = sdf.format(created);
                        Log.d("OBJ_DATE", created_sdf);
                        task.put("createdAt", created_sdf);
                        taskList.add(task);
                    }
                    Log.d("LIST", String.valueOf(taskList));

                    ListAdapter simpleAdapter = new SimpleAdapter(Tasks.this,
                            taskList,
                            R.layout.task_list,
                            new String[]{"description", "createdAt", "objectId"},
                            new int[]{R.id.description, R.id.createdAt, R.id.objectId});
                    listview.setAdapter(simpleAdapter);
                } else {
                    Log.d("tasks", "Error: " + e.getMessage());
                }
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentTaskId != null) {
                    Log.d("OBJECT", currentTaskId);
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Tasks");
                    query.getInBackground(currentTaskId, new GetCallback<ParseObject>() {
                        public void done(ParseObject task, ParseException e) {
                            if (e == null) {
                                task.put("completed", true);
                                task.remove("assignedTo");
                                task.saveInBackground();
                            }
                            Snackbar.make(v, "Task complete.", Snackbar.LENGTH_LONG).show();
                            //recreate();  // Flashes screen, method below has smoother results
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                        }
                    });
                }
                else {
                    Log.d("OBJECT", "Object is null");
                }
            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentTaskId != null) {
                    Log.d("OBJECT", currentTaskId);
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Tasks");
                    query.getInBackground(currentTaskId, new GetCallback<ParseObject>() {
                        public void done(ParseObject task, ParseException e) {
                            if (e == null) {
                                task.remove("assignedTo");
                                task.saveInBackground();
                            }
                            Snackbar.make(v, "Task incomplete. Returned to shared list.", Snackbar.LENGTH_LONG).show();
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                        }
                    });
                }
                else {
                    Log.d("OBJECT", "Object is null");
                }
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> selected_task = (HashMap<String, String>) parent.getItemAtPosition(position);
                String objectId_clicked = selected_task.get("objectId");
                Log.d("OBJECT_ID", objectId_clicked);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Tasks.this);
                alertDialogBuilder.setMessage("Accept the selected task?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                ParseQuery<ParseObject> query = ParseQuery.getQuery("Tasks");
                                query.getInBackground(objectId_clicked, new GetCallback<ParseObject>() {
                                    public void done(ParseObject task, ParseException e) {
                                        if (e == null) {
                                            task.put("assignedTo", user);
                                            task.saveInBackground();
                                        }
                                        Snackbar.make(view, "Selection assigned as current task.", Snackbar.LENGTH_LONG).show();
                                        finish();
                                        overridePendingTransition(0, 0);
                                        startActivity(getIntent());
                                        overridePendingTransition(0, 0);
                                    }
                                });
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }
}

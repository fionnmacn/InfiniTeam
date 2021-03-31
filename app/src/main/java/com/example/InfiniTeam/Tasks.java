package com.example.InfiniTeam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import java.util.List;

public class Tasks extends AppCompatActivity implements TaskAdapter.SelectedTask {

    RecyclerView recyclerView;
    FloatingActionButton fab;
    TextView currentTask;
    Button accept;
    Button complete;
    Button decline;
    String currentTaskId = null;
    boolean activeTask = false;

    List<TaskModel> taskModelList = new ArrayList<>();
    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        recyclerView = findViewById(R.id.task_recyclerView);
        fab = findViewById(R.id.task_create);
        currentTask = findViewById(R.id.currentTask);
        complete = findViewById(R.id.task_complete);
        decline = findViewById(R.id.task_decline);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        ParseUser user = ParseUser.getCurrentUser();
        String currentUser = user.getUsername();
        Log.d("USER", currentUser);
        boolean elevated = user.getBoolean("elevated");
        Log.d("ELEVATED", String.valueOf(elevated));

        if (elevated) {
            fab.show();
        } else {
            fab.hide();
        }

        /*
        Parse Query to check if any of the Tasks are currently assigned to the logged in user.
        If so, shows the buttons and sets the activeTask variable to true.
         */
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Tasks");
        query1.whereEqualTo("assignedTo", currentUser);
        query1.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    String description = object.getString("description");
                    currentTaskId = object.getObjectId();
                    Log.d("CURRENT_TASK", description);
                    currentTask.setText(description);
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

        /*
        Parse Query to fetch all tasks which are not completed and which are not currently assigned
        to another user. Creates a TaskModel object and passes it to the recyclerView.
         */
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Tasks");
        query2.whereEqualTo("completed", false);
        query2.orderByDescending("createdAt");
        query2.whereEqualTo("assignedTo", null);
        query2.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> taskList, ParseException e) {
                for (int i = 0; i < taskList.size(); i++) {
                    ParseObject object = taskList.get(i);
                    Log.d("OBJECT", object.toString());

                    String id = object.getObjectId();
                    Log.d("TASK_ID", id);
                    String description = object.getString("description");
                    Log.d("TASK_DS", description);
                    boolean priority = object.getBoolean("priority");
                    Log.d("TASK_PR", String.valueOf(priority));

                    Date createdAt = object.getCreatedAt();
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, hh:mm aaa");
                    String created_sdf = sdf.format(createdAt);
                    Log.d("TASK_CA", created_sdf);

                    TaskModel taskModel = new TaskModel(id, description, priority, created_sdf);
                    taskModelList.add(taskModel);
                }
                callAdapter(taskModelList);
            }
        });

        /*
        Sets the task currently assigned to the user as complete and restarts the activity
         */
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

        /*
        The task is no longer assigned to the user and made available on the shared list to all.
         */
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
                            Snackbar.make(v, "Task incomplete. Returned to shared list.",
                                    Snackbar.LENGTH_LONG).show();
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Tasks.this, CreateTask.class));
            }
        });
    }

    @Override
    public void selectedTask(TaskModel taskModel) {
        startActivity(new Intent(Tasks.this, SelectedTask.class)
                .putExtra("data", taskModel));
    }

    public void callAdapter(List<TaskModel> taskModelList) {
        Log.d("LIST_OUT", "List should be after this");
        Log.d("LIST_OUT", taskModelList.toString());
        taskAdapter = new TaskAdapter(taskModelList, this);
        recyclerView.setAdapter(taskAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                taskAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.search_view) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
package com.example.InfiniTeam.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.InfiniTeam.R;
import com.example.InfiniTeam.models.TaskModel;
import com.google.android.material.snackbar.Snackbar;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class SelectedTask extends AppCompatActivity {

    String id = null;
    TextView description;
    TextView createdAt;
    Button accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_task);

        description = findViewById(R.id.task_selected_description);
        createdAt = findViewById(R.id.task_selected_createdAt);
        accept = findViewById(R.id.task_selected_accept);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            TaskModel taskModel = (TaskModel) intent.getSerializableExtra("data");

            id = taskModel.getId();
            description.setText(taskModel.getDescription());
            createdAt.setText((CharSequence) taskModel.getCreatedAt());
        }

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                String username = currentUser.getString("username");
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Tasks");
                query.getInBackground(id, new GetCallback<ParseObject>() {
                    public void done(ParseObject task, ParseException e) {
                        if (e == null) {
                            task.add("assignedTo", username);
                            task.saveInBackground();
                            Snackbar.make(v, "Task accepted.", Snackbar.LENGTH_LONG).show();
                            SelectedTask.this.finish();
                        }
                        else {
                            Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}

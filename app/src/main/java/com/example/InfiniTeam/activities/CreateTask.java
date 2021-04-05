package com.example.InfiniTeam.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.InfiniTeam.R;
import com.google.android.material.snackbar.Snackbar;
import com.parse.ParseObject;

public class CreateTask extends AppCompatActivity {

    EditText description;
    SwitchCompat priority;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        description = findViewById(R.id.create_task_description);
        priority = findViewById(R.id.create_task_priority);
        send = findViewById(R.id.create_task_button);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CREATE_TASK", String.valueOf(description.getText()));
                Log.d("CREATE_TASK", String.valueOf(priority.isChecked()));
                Snackbar.make(v, "Task complete.", Snackbar.LENGTH_LONG).show();

                ParseObject task = new ParseObject("Tasks");
                task.put("description", String.valueOf(description.getText()));
                task.put("completed", false);
                task.put("priority", priority.isChecked());
                task.saveInBackground();

                Intent intent = new Intent(CreateTask.this, Tasks.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
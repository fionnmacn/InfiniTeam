package com.example.prototype;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SendNotice extends AppCompatActivity {

    TextView subject;
    TextView content;
    SwitchCompat priority;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notice);
        subject = findViewById(R.id.send_notice_subject);
        content = findViewById(R.id.send_notice_content);
        priority = findViewById(R.id.send_notice_priority);
        send = findViewById(R.id.send_notice_button);


    }
}
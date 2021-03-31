package com.example.InfiniTeam;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.parse.ParseObject;

public class SendNotice extends AppCompatActivity {

    EditText subject;
    EditText content;
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

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MESSAGE", String.valueOf(subject.getText()));
                Log.d("MESSAGE", String.valueOf(content.getText()));
                Log.d("MESSAGE", String.valueOf(priority.isChecked()));

                ParseObject notice = new ParseObject("Notices");
                notice.put("subject", String.valueOf(subject.getText()));
                notice.put("content", String.valueOf(subject.getText()));
                notice.put("priority", priority.isChecked());
                notice.saveInBackground();
            }
        });
    }
}
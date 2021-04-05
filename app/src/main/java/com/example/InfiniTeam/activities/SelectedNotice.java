package com.example.InfiniTeam.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.InfiniTeam.R;
import com.example.InfiniTeam.models.NoticeModel;
import com.google.android.material.snackbar.Snackbar;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class SelectedNotice extends AppCompatActivity {

    String id = null;
    TextView subject;
    TextView content;
    Button archive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_notice);

        subject = findViewById(R.id.notice_selected_subject);
        content = findViewById(R.id.notice_selected_content);
        archive = findViewById(R.id.archive_button);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            NoticeModel noticeModel = (NoticeModel) intent.getSerializableExtra("data");

            id = noticeModel.getId();
            subject.setText(noticeModel.getSubject());
            content.setText(noticeModel.getContent());
        }

        archive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                String username = currentUser.getString("username");
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Notices");
                query.getInBackground(id, new GetCallback<ParseObject>() {
                    public void done(ParseObject notice, ParseException e) {
                        if (e == null) {
                            notice.add("dismissed", username);
                            notice.saveInBackground();
                            Snackbar.make(v, "Notice archived.", Snackbar.LENGTH_LONG).show();
                            SelectedNotice.this.finish();
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

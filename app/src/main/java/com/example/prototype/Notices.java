package com.example.prototype;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Notices extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;

    List<NoticeModel> noticeModelList = new ArrayList<>();
    NoticeAdapter noticeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notices);
        recyclerView = findViewById(R.id.notice_recyclerView);
        toolbar = findViewById(R.id.notice_toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        ParseUser user = ParseUser.getCurrentUser();
        String currentUser = user.getUsername();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Notices");
        query.whereNotEqualTo("dismissed", currentUser);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> noticeList, ParseException e) {
                for (int i = 0; i < noticeList.size(); i++) {
                    HashMap<String, String> notices = new HashMap<>();
                    ParseObject object = noticeList.get(i);
                    Log.d("OBJECT", object.toString());

                    String id = object.getObjectId();
                    Log.d("NOTICE", id);
                    String subject = object.getString("subject");
                    Log.d("NOTICE", subject);
                    String content = object.getString("content");
                    Log.d("NOTICE", content);

                    NoticeModel noticeModel = new NoticeModel(id, subject, content);
                    noticeModelList.add(noticeModel);
//                    Log.d("NOTICES", noticeModelList.toString());
                }
                noticeAdapter = new NoticeAdapter(noticeModelList);
                recyclerView.setAdapter(noticeAdapter);
            }
        });
    }
}
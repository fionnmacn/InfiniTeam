package com.example.prototype;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Notices extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;

    List<NoticeModel> noticeModelList = new ArrayList<>();

//    String[] objectIds = {"abc123", "aaa555", "911911"};
    String[] subjects = {"Staff Meeting", "Important Reminder", "Holiday Requests", "Staff Party", "Safety Precautions", "Security Concerns"};
//    String[] contents = {"Staff meeting on the 28th October", "Remember to check €50 notes", "All holiday requests must be made through a manager"};

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

        for (String s:subjects) {
            NoticeModel noticeModel = new NoticeModel(s);
            noticeModelList.add(noticeModel);
        }

        noticeAdapter = new NoticeAdapter(noticeModelList);
        recyclerView.setAdapter(noticeAdapter);
    }
}
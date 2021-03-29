package com.example.prototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void openProfile(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void openTasks(View view) {
        Intent intent = new Intent(this, Tasks.class);
        startActivity(intent);
    }

    public void openBreaks(View view) {
        Intent intent = new Intent(this, Hours.class);
        startActivity(intent);
    }

    public void openSearch(View view) {
        Intent intent = new Intent(this, Products.class);
        startActivity(intent);
    }

    public void openNotices(View view) {
        Intent intent = new Intent(this, Notices.class);
        startActivity(intent);
    }
}
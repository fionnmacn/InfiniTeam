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

    public void openTasks(View view) {
        Intent intent = new Intent(this, Tasks.class);
        startActivity(intent);
    }

    public void openSearch(View view) {
        Intent intent = new Intent(this, ProductSearch.class);
        startActivity(intent);
    }
}
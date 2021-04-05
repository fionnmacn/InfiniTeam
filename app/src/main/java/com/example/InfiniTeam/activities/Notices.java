package com.example.InfiniTeam.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.InfiniTeam.R;
import com.example.InfiniTeam.adapters.NoticeAdapter;
import com.example.InfiniTeam.models.NoticeModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Notices extends AppCompatActivity implements NoticeAdapter.SelectedNotice{
    Toolbar toolbar;
    RecyclerView recyclerView;
    FloatingActionButton fab;

    List<NoticeModel> noticeModelList = new ArrayList<>();
    NoticeAdapter noticeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notices);
        recyclerView = findViewById(R.id.notice_recyclerView);
        toolbar = findViewById(R.id.notice_toolbar);
        fab = findViewById(R.id.notice_create);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

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

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Notices");
        query.whereNotEqualTo("dismissed", currentUser);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> noticeList, ParseException e) {
                for (int i = 0; i < noticeList.size(); i++) {
                    ParseObject object = noticeList.get(i);
                    Log.d("OBJECT", object.toString());

                    String id = object.getObjectId();
                    Log.d("NOTICE", id);
                    String subject = object.getString("subject");
                    Log.d("NOTICE", subject);
                    String content = object.getString("content");
                    Log.d("NOTICE", content);
                    boolean priority = object.getBoolean("priority");
                    Log.d("NOTICE", String.valueOf(priority));

                    NoticeModel noticeModel = new NoticeModel(id, subject, content, priority);
                    noticeModelList.add(noticeModel);
                }
                callAdapter(noticeModelList);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Notices.this, SendNotice.class));
            }
        });
    }

    @Override
    public void selectedNotice(NoticeModel noticeModel) {
        startActivity(new Intent(Notices.this, SelectedNotice.class).putExtra("data", noticeModel));
    }

    public void callAdapter(List<NoticeModel> noticeModelList) {
        Log.d("LIST_OUT", "List should be after this");
        Log.d("LIST_OUT", noticeModelList.toString());
        noticeAdapter = new NoticeAdapter(noticeModelList, this);
        recyclerView.setAdapter(noticeAdapter);
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
                noticeAdapter.getFilter().filter(newText);
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
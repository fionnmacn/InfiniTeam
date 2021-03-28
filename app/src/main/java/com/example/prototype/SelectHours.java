package com.example.prototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class SelectHours extends AppCompatActivity {
    TextView start;
    TextView end;
    Button accept;
    String objectId;
    String start_date;
    String end_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_hours);
        start = findViewById(R.id.start_date);
        end = findViewById(R.id.end_date);
        accept = findViewById(R.id.add_button);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if(b != null)
        {
            objectId = (String) b.get("objectId");
            start_date = (String) b.get("start");
            start.setText(start_date);
            end_date = (String) (b.get("end"));
            end.setText(end_date);
        }

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                String username = currentUser.getString("username");
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Shifts");
                query.getInBackground(objectId, new GetCallback<ParseObject>() {
                    public void done(ParseObject hours, ParseException e) {
                        if (e == null) {
                            hours.put("giveaway", false);
                            hours.put("user", username);
                            hours.saveInBackground();
                            Snackbar.make(v, "Shift added to rota", Snackbar.LENGTH_LONG).show();
                            SelectHours.this.finish();
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
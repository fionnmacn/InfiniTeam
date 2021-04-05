package com.example.InfiniTeam.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.InfiniTeam.R;
import com.example.InfiniTeam.models.ProductModel;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Settings extends AppCompatActivity {

    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        logout = findViewById(R.id.logout);

        HashMap<String, String> params = new HashMap<>();
        ParseCloud.callFunctionInBackground("restdb", params, new FunctionCallback<ArrayList>() {
            public void done(ArrayList response, ParseException e) {
                if (e == null) {
                    Log.d("RESPONSE", response.toString());
                    JSONArray jsArray = new JSONArray(response);
                    Log.d("newJSON", jsArray.toString());
                    try {
                        for (int i = 0; i < jsArray.length(); i++) {
                            JSONObject obj = jsArray.getJSONObject(i);
                            String id = obj.getString("id");
                            Log.d("id", id);
                            String name = obj.getString("name");
                            String description = obj.getString("description");
                            String extra1 = obj.getString("extra1");
                            String extra2 = obj.getString("extra2");
                            String image = obj.getString("image");
                            Log.d("image", image);
                        }
                    } catch (JSONException ex) {
                        Log.e("JsonParserIn", "Exception", ex);
                    }
                } else {
                    Log.e("cloudCode", e.toString());
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                Toast.makeText(getApplicationContext(), "Logged out.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
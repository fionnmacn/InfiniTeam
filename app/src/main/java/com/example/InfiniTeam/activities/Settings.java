package com.example.InfiniTeam.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.InfiniTeam.R;
import com.example.InfiniTeam.models.ProductModel;
import com.example.InfiniTeam.models.StockModel;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button logout;
    Spinner select_store;
    SharedPreferences shared_pref;
    SharedPreferences.Editor shared_edit;
    ArrayList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        logout = findViewById(R.id.logout);
        select_store = findViewById(R.id.settings_spinner);
        select_store.setOnItemSelectedListener(this);
        shared_pref = getSharedPreferences("InfiniTeam", MODE_PRIVATE);
        shared_edit = shared_pref.edit();

        ArrayList<String> list_of_stores = new ArrayList<>();
        HashMap<String, String> params = new HashMap<>();
        ParseCloud.callFunctionInBackground("retrieveStores", params, new FunctionCallback<ArrayList>() {
            public void done(ArrayList response, ParseException e) {
                if (e == null) {
                    list = response;
                    Log.d("list_of_stores", String.valueOf(list));
                    ArrayAdapter adapter = new ArrayAdapter(Settings.this, android.R.layout.simple_spinner_item, list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    select_store.setAdapter(adapter);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        shared_edit.putString("current_store", String.valueOf(parent.getItemAtPosition(position)));
        shared_edit.putString("store_pos", String.valueOf(position));
        shared_edit.commit();
        Log.d("STORE", shared_pref.getString("current_store", ""));
        Log.d("STORE", shared_pref.getString("store_pos", ""));
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
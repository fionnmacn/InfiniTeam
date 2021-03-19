package com.example.prototype;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Breaks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breaks);
        TextView test = findViewById(R.id.breaksText1);

        HashMap<String, String> params = new HashMap<>();
        ParseCloud.callFunctionInBackground("breaks", params, (FunctionCallback< Map<String, Object> >) (cloud_response, e) -> {
            if (e == null) {
                Log.d("cloudCode", cloud_response.toString());
                String id = cloud_response.get("name").toString();
                test.setText(id);
            }
            else {
                Log.e("cloud_code", "Cloud Code Error: " + e.getMessage());
                runOnUiThread(() -> Toast.makeText(getApplicationContext(),
                        "Error retrieving data", Toast.LENGTH_LONG).show());
            }
        });
    }
}

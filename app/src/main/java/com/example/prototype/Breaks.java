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

    private static final String TAG = Breaks.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breaks);

        TextView test = findViewById(R.id.breaksText1);

        Map<String, String> parameters = new HashMap<>();
        ParseCloud.callFunctionInBackground("test", parameters, (FunctionCallback<JSONObject>) (cloud_response, e) -> {
            if (e == null) {
                try {
                    String id = cloud_response.getString("id");
                    test.setText(id);
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            }
            else {
                Log.e(TAG, "Cloud Code Error: " + e.getMessage());
                runOnUiThread(() -> Toast.makeText(getApplicationContext(),
                        "Error retrieving data", Toast.LENGTH_LONG).show());
            }
        });
    }
}

package com.example.InfiniTeam;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductSearch3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ListView listview = findViewById(R.id.list);

        HashMap<String, String> params = new HashMap<>();
        ParseCloud.callFunctionInBackground("products", params, new FunctionCallback<ArrayList>() {
            public void done(ArrayList response, ParseException e) {
                if (e == null) {
                    Log.d("RESPONSE", response.toString());
                    JSONArray jsArray = new JSONArray(response);
                    Log.d("newJSON", jsArray.toString());
                    try {
                        ArrayList<HashMap<String, String>> userList = new ArrayList<>();

                        for (int i = 0; i < jsArray.length(); i++) {
                            HashMap<String, String> stu = new HashMap<>();
                            JSONObject obj = jsArray.getJSONObject(i);
                            stu.put("id", obj.getString("id"));
                            stu.put("name", obj.getString("name"));
                            userList.add(stu);
                        }
                        Log.d("listArray", userList.toString());
                        ListAdapter simpleAdapter = new SimpleAdapter(ProductSearch3.this,
                                userList,
                                R.layout.list,
                                new String[]{"id", "name"},
                                new int[]{R.id.id, R.id.name});
                        listview.setAdapter(simpleAdapter);
                    } catch (JSONException ex) {
                        Log.e("JsonParserIn", "Exception", ex);
                    }
                } else {
                    Log.e("cloudCode", e.toString());
                }
            }
        });
    }
}
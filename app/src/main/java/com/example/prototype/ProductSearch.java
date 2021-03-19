package com.example.prototype;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductSearch extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ListView listview = findViewById(R.id.list);

        HashMap<String, String> params = new HashMap<>();
        ParseCloud.callFunctionInBackground("products", params, new FunctionCallback<ArrayList>() {
            public void done(ArrayList data, ParseException e) {
                if (e == null) {
                    Log.d("RESPONSE", data.toString());
                }
//                try {
//                    Log.d("response", response);
//                    JSONObject jsObj = new JSONObject(response);
//                    Log.d("newJSON", jsObj.toString());
//                    try {
//                        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
//
//                        JSONArray jsArray = jsObj.getJSONArray("products");
//                        for (int i = 0; i < jsArray.length(); i++) {
//                            HashMap<String, String> stu = new HashMap<>();
//                            JSONObject obj = jsArray.getJSONObject(i);
//                            stu.put("id", obj.getString("id"));
//                            stu.put("name", obj.getString("name"));
//                            userList.add(stu);
//                        }
//                        Log.d("listArray", userList.toString());
//                        ListAdapter simpleAdapter = new SimpleAdapter(ProductSearch.this,
//                                userList,
//                                R.layout.list,
//                                new String[]{"id", "name"},
//                                new int[]{R.id.id, R.id.name});
//                        listview.setAdapter(simpleAdapter);
//                    } catch (JSONException ex) {
//                        Log.e("JsonParserIn", "Exception", ex);
//                    }
//                } catch (JSONException jsonException) {
//                    Log.e("JsonParserOut", "Exception", jsonException);
//                }
                else {
                    Log.e("cloudCode", e.toString());
                }
            }
        });
    }
}
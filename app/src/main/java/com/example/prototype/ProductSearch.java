package com.example.prototype;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductSearch extends AppCompatActivity {

    private final String TAG = ProductSearch.class.getSimpleName();
    ArrayList<HashMap<String, String>> productList;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        productList = new ArrayList<>();
        new GetData().execute();
        lv = (ListView) findViewById(R.id.list);
    }

    private class GetData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Map<String, String> parameters = new HashMap<>();
            ParseCloud.callFunctionInBackground("test", parameters, (FunctionCallback<String>) (cloud_response, e) -> {
                if (cloud_response != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(cloud_response);
                        // Getting JSON Array node
                        JSONArray products = jsonObj.getJSONArray("products");
                        // loop through all nodes
                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);
                            String id = c.getString("id");
                            String name = c.getString("name");

                            // hash map for single item
                            HashMap<String, String> product = new HashMap<>();

                            // adding each node to HashMap
                            product.put("id", id);
                            product.put("name", name);
//                            for (int j = 0; j < productDetails.length(); j++) {
//                                JSONObject detail = productDetails.getJSONObject(j);
//                                if (detail.getString("id").equals(id)) {
//                                    procedure.put("category", detail.getString("category"));
//                                }
//                            }
                            // add data to list
                            productList.add(product);
                        }
                        Log.e(TAG, productList.toString());
                    } catch (final JSONException err) {
                        Log.e(TAG, "Error: " + err.getMessage());
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(),
                                "Error: " + err.getMessage(),
                                Toast.LENGTH_LONG).show());
                    }
                } else {
                    Log.e(TAG, "Couldn't get data from server.");
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(),
                            "Couldn't get data from server: " + e.getMessage(),
                            Toast.LENGTH_LONG).show());
                }
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(ProductSearch.this,
                    productList,
                    R.layout.list,
                    new String[]{"name","id"},
                    new int[]{R.id.name, R.id.id});

            lv.setAdapter(adapter);

        }
    }
}

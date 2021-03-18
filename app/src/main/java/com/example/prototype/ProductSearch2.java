package com.example.prototype;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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

public class ProductSearch2 extends AppCompatActivity {

    // Instantiates the objects
    ListView listView;
    ArrayList<Product> arrayList;
    ProductAdapter customAdapter;

    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listView = findViewById(R.id.list);

//        Button add = findViewById(R.id.addButton);
//        add.setOnClickListener((View.OnClickListener) this); // calling onClick() method

        ArrayList<Product> productList = new ArrayList<>();
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
                    Log.e("output_list", productList.toString());
                } catch (final JSONException err) {
                    Log.e("json", "Error: " + err.getMessage());
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(),
                            "Error: " + err.getMessage(),
                            Toast.LENGTH_LONG).show());
                }
            } else {
                Log.e("parse", "Couldn't get data from server.");
                runOnUiThread(() -> Toast.makeText(getApplicationContext(),
                        "Couldn't get data from server: " + e.getMessage(),
                        Toast.LENGTH_LONG).show());
            }
        });

        // Inserts some rows in the products table
        productDatabaseHandler.insertProduct("123/9999", "Black Bedsheet", "Black bedsheet for double bed", "84HK");

        arrayList = new ArrayList<>();
        loadDataInListView();
    }

    public void onClick(View v) {
        switch (v.getId()) {
                /*
            case R.id.addButton:
                Intent addIntent = new Intent(MainActivity.this, AddProductActivity.class);
                startActivity(addIntent);
                break;

            case R.id.deleteButton:
                Intent deleteIntent = new Intent(MainActivity.this, DeleteProductActivity.class);
                startActivity(deleteIntent);
                break;

            case R.id.supportButton:
                Intent supportIntent = new Intent(MainActivity.this, SupportActivity.class);
                startActivity(supportIntent);
                break;

            default:
                break;
                 */
        }
    }

    private void loadDataInListView() {
        arrayList = productDatabaseHandler.getAllData();
        customAdapter = new ProductAdapter(this.arrayList);
        listView.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();
    }
}
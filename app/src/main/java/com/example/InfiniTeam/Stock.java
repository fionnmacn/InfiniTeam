package com.example.InfiniTeam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Stock extends AppCompatActivity {

    ImageView image;
    TextView id;
    TextView name;
    TextView description;
    TextView extra1;
    TextView extra2;
    TextView price;
    TextView stock;
    Button stock_button;
    String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        name = findViewById(R.id.stock);

        productId = getIntent().getStringExtra("id");

        HashMap<String, String> params = new HashMap<>();
        params.put("id", productId);
        ParseCloud.callFunctionInBackground("stock", params, new FunctionCallback<ArrayList>() {
            public void done(ArrayList response, ParseException e) {
                if (e == null) {
                    Log.d("RESPONSE", response.toString());
                    JSONArray jsArray = new JSONArray(response);
                    Log.d("newJSON", jsArray.toString());
//                    try {
//                        for (int i = 0; i < jsArray.length(); i++) {
//                            JSONObject obj = jsArray.getJSONObject(i);
//                            String id = obj.getString("id");
//                            String name = obj.getString("name");
//                            String description = obj.getString("description");
//                            String extra1 = obj.getString("extra1");
//                            String extra2 = obj.getString("extra2");
//                            int price = 0;
//                            int stock = 0;
//                            String image = obj.getString("image");
//
//                            ProductModel productModel = new ProductModel(id, name, description, extra1, extra2, price, stock, image);
//                            productModelList.add(productModel);
//                        }
//                        callAdapter(productModelList);
//                        Log.d("listArray", productModelList.toString());
//                    } catch (JSONException ex) {
//                        Log.e("JsonParserIn", "Exception", ex);
//                    }
                } else {
                    Log.e("cloudCode", e.toString());
                }
            }
        });
    }
}

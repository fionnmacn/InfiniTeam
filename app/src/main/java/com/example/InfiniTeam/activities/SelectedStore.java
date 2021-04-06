package com.example.InfiniTeam.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.InfiniTeam.R;
import com.example.InfiniTeam.models.StockModel;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import java.util.HashMap;

public class SelectedStore extends AppCompatActivity {

    TextView store;
    TextView stock;
    EditText amount;
    Button reserve_button;
    int new_stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_store);

        store = findViewById(R.id.store_selected_name);
        stock = findViewById(R.id.store_selected_stock);
        reserve_button = findViewById(R.id.reserve_button);
        amount = findViewById(R.id.store_selected_number);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            StockModel stockModel = (StockModel) intent.getSerializableExtra("stockModel");
            String productId = intent.getStringExtra("productId");

            String intent_store = stockModel.getStore();
            int intent_stock = stockModel.getStock();
            store.setText(intent_store);
            stock.setText(String.valueOf(intent_stock));

            reserve_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int amount_int = Integer.parseInt(amount.getText().toString());
                        new_stock = intent_stock - amount_int;
                        Log.d("ERRRRRRRROR_TRY", String.valueOf(new_stock));

                        Log.d("ERRRRRRRROR_CLICK", String.valueOf(new_stock));
                        HashMap<String, String> params = new HashMap<>();
                        params.put("id", productId);
                        params.put("name", intent_store);
                        params.put("reserve", String.valueOf(new_stock));
                        ParseCloud.callFunctionInBackground("updateStock", params,
                                new FunctionCallback<String>() {
                                    public void done(String response, ParseException e) {
                                        if (e == null) {
                                            if (response != null) {
                                                Log.d("UPDATE_RESPONSE", response);
                                            }
                                        } else {
                                            Log.e("UPDATE_cloudCode", e.toString());
                                        }
                                    }
                                });
                    } catch (Exception ex) {
                        Log.d("ERRRRRRRROR_CATCH", ex.getMessage());
                    }


                }
            });
        }
    }
}

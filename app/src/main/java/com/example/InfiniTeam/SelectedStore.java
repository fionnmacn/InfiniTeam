package com.example.InfiniTeam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class SelectedStore extends AppCompatActivity {

    TextView store;
    TextView stock;
    Button reserve_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_store);

        store = findViewById(R.id.store_selected_name);
        stock = findViewById(R.id.store_selected_stock);
        reserve_button = findViewById(R.id.reserve_button);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            StockModel stockModel = (StockModel) intent.getSerializableExtra("data");

            String intent_store = stockModel.getStore();
            String intent_stock = String.valueOf(stockModel.getStock());
            store.setText(intent_store);
            stock.setText(intent_stock);
        }

        reserve_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SelectedStore.this, "Stock successfully reserved",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SelectedStore.this, Products.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

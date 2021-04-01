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

import androidx.appcompat.app.AppCompatActivity;

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
    Button stock_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_product);

        store = findViewById(R.id.product_selected_id);
        stock = findViewById(R.id.product_selected_stock);
        stock_button = findViewById(R.id.product_selected_stock_button);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            ProductModel productModel = (ProductModel) intent.getSerializableExtra("data");

            stock.setText("16");


//            price.setText((int) productModel.getPrice());
//            stock.setText(productModel.getStock());
        }

//        stock_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SelectedProduct.this, Stock.class)
//                        .putExtra("id", id.getText());
//                startActivity(intent);
//                finish();
//            }
//        });
    }
}

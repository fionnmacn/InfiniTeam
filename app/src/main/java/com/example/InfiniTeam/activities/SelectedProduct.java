package com.example.InfiniTeam.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.InfiniTeam.R;
import com.example.InfiniTeam.models.ProductModel;

public class SelectedProduct extends AppCompatActivity {

    ImageView image;
    TextView id;
    TextView name;
    TextView description;
    TextView extra1;
    TextView extra2;
    TextView price;
    TextView stock;
    Button stock_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_product);

        image = findViewById(R.id.product_selected_image);
        id = findViewById(R.id.product_selected_id);
        name = findViewById(R.id.product_selected_name);
        description = findViewById(R.id.product_selected_description);
        extra1 = findViewById(R.id.product_selected_extra1);
        extra2 = findViewById(R.id.product_selected_extra2);
        price = findViewById(R.id.product_selected_price);
        stock = findViewById(R.id.product_selected_stock);
        stock_button = findViewById(R.id.product_selected_stock_button);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            ProductModel productModel = (ProductModel) intent.getSerializableExtra("data");

            id.setText(productModel.getId());
            name.setText(productModel.getName());
            description.setText(productModel.getDescription());
            extra1.setText(productModel.getExtra1());
            extra2.setText(productModel.getExtra2());
            price.setText("12.95");
            stock.setText("16");


//            price.setText((int) productModel.getPrice());
//            stock.setText(productModel.getStock());
        }

        stock_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectedProduct.this, Stock.class)
                        .putExtra("id", id.getText());
                startActivity(intent);
                finish();
            }
        });
    }
}

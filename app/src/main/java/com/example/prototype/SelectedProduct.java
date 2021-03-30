package com.example.prototype;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class SelectedProduct extends AppCompatActivity {

    TextView id;
    TextView name;
    TextView description;
    TextView extra1;
    TextView extra2;
    TextView price;
    TextView stock;
    Button archive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_product);

        id = findViewById(R.id.product_selected_id);
        name = findViewById(R.id.product_selected_name);
        description = findViewById(R.id.product_selected_description);
        extra1 = findViewById(R.id.product_selected_extra1);
        extra2 = findViewById(R.id.product_selected_extra2);
        price = findViewById(R.id.product_selected_price);
        stock = findViewById(R.id.product_selected_stock);
        archive = findViewById(R.id.archive_button);

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

//        archive.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ParseUser currentUser = ParseUser.getCurrentUser();
//                String username = currentUser.getString("username");
//                ParseQuery<ParseObject> query = ParseQuery.getQuery("Notices");
//                query.getInBackground(id, new GetCallback<ParseObject>() {
//                    public void done(ParseObject product, ParseException e) {
//                        if (e == null) {
//                            product.add("dismissed", username);
//                            product.saveInBackground();
//                            Snackbar.make(v, "Notice archived.", Snackbar.LENGTH_LONG).show();
//                            SelectedProduct.this.finish();
//                        }
//                        else {
//                            Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_LONG).show();
//                        }
//                    }
//                });
//            }
//        });
    }
}

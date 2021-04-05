package com.example.InfiniTeam.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.InfiniTeam.R;
import com.example.InfiniTeam.adapters.ProductAdapter;
import com.example.InfiniTeam.models.ProductModel;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Products extends AppCompatActivity implements ProductAdapter.SelectedProduct{
    Toolbar toolbar;
    RecyclerView recyclerView;

    List<ProductModel> productModelList = new ArrayList<>();
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        recyclerView = findViewById(R.id.product_recyclerView);
        toolbar = findViewById(R.id.product_toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

//        ParseUser user = ParseUser.getCurrentUser();
//        String currentUser = user.getUsername();

        HashMap<String, String> params = new HashMap<>();
        ParseCloud.callFunctionInBackground("products", params, new FunctionCallback<ArrayList>() {
            public void done(ArrayList response, ParseException e) {
                if (e == null) {
                    Log.d("RESPONSE", response.toString());
                    JSONArray jsArray = new JSONArray(response);
                    Log.d("newJSON", jsArray.toString());
                    try {
                        for (int i = 0; i < jsArray.length(); i++) {
                            JSONObject obj = jsArray.getJSONObject(i);
                            String id = obj.getString("id");
                            String name = obj.getString("name");
                            String description = obj.getString("description");
                            String extra1 = obj.getString("extra1");
                            String extra2 = obj.getString("extra2");
                            int price = 0;
                            int stock = 0;
                            String image = obj.getString("image");

                            ProductModel productModel = new ProductModel(id, name, description, extra1, extra2, price, stock, image);
                            productModelList.add(productModel);
                        }
                        callAdapter(productModelList);
                        Log.d("listArray", productModelList.toString());
                    } catch (JSONException ex) {
                        Log.e("JsonParserIn", "Exception", ex);
                    }
                } else {
                    Log.e("cloudCode", e.toString());
                }
            }
        });
    }

    @Override
    public void selectedProduct(ProductModel productModel) {
        startActivity(new Intent(Products.this, SelectedProduct.class).putExtra("data", productModel));
    }

    public void callAdapter(List<ProductModel> productModelList) {
        Log.d("LIST_OUT", "List should be after this");
        Log.d("LIST_OUT", productModelList.toString());
        productAdapter = new ProductAdapter(productModelList, this);
        recyclerView.setAdapter(productAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.search_view) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
package com.example.InfiniTeam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Stock extends AppCompatActivity implements StockAdapter.SelectedStore {
    Toolbar toolbar;
    RecyclerView recyclerView;
    FloatingActionButton fab;

    List<StockModel> stockModelList = new ArrayList<>();
    StockAdapter stockAdapter;
    String productId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        recyclerView = findViewById(R.id.stock_recyclerView);
        toolbar = findViewById(R.id.stock_toolbar);
        fab = findViewById(R.id.stock_create);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        ParseUser user = ParseUser.getCurrentUser();
        String currentUser = user.getUsername();
        Log.d("USER", currentUser);
        boolean elevated = user.getBoolean("elevated");
        Log.d("ELEVATED", String.valueOf(elevated));

        if (elevated) {
            fab.show();
        } else {
            fab.hide();
        }

        productId = getIntent().getStringExtra("id");

        HashMap<String, String> params = new HashMap<>();
        params.put("id", productId);
        ParseCloud.callFunctionInBackground("stock", params, new FunctionCallback<ArrayList>() {
            public void done(ArrayList response, ParseException e) {
                if (e == null) {
                    Log.d("RESPONSE", response.toString());
                    JSONArray jsArray = new JSONArray(response);
                    Log.d("newJSON", jsArray.toString());
                    HashMap<String, String> stores = new HashMap<>();
                    try {
                        for (int i = 0; i < jsArray.length(); i++) {
                            JSONObject obj = jsArray.getJSONObject(i);

                            Iterator<String> keys= obj.keys();
                            while (keys.hasNext())
                            {
                                String keyValue = (String)keys.next();
                                Log.d("store_name", keyValue);
//                                String valueString = obj.getString(keyValue);
//                                Log.d("_____TEST_____", valueString);
                                int stockLevel = Integer.parseInt(obj.getString(keyValue));
                                Log.d("stock_level", String.valueOf(stockLevel));
//                                stores.put(keyValue, stockLevel);
                                StockModel stockModel = new StockModel(keyValue, stockLevel);
                                stockModelList.add(stockModel);
                            }
                            Log.d("FINISHED_LIST", String.valueOf(stockModelList));
                            callAdapter(stockModelList);
                            Log.d("STOCK_MODEL_LIST", stockModelList.toString());
                        }
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
    public void selectedStore(StockModel stockModel) {
        startActivity(new Intent(Stock.this, SelectedStore.class).putExtra("data", stockModel));
    }

    public void callAdapter(List<StockModel> stockModelList) {
        Log.d("LIST_OUT", "List should be after this");
        Log.d("LIST_OUT", stockModelList.toString());
        stockAdapter = new StockAdapter(stockModelList, this);
        recyclerView.setAdapter(stockAdapter);
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
                stockAdapter.getFilter().filter(newText);
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

//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Notices");
//        query.whereNotEqualTo("dismissed", currentUser);
//        query.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> noticeList, ParseException e) {
//                for (int i = 0; i < noticeList.size(); i++) {
//                    ParseObject object = noticeList.get(i);
//                    Log.d("OBJECT", object.toString());
//
//                    String id = object.getObjectId();
//                    Log.d("NOTICE", id);
//                    String subject = object.getString("subject");
//                    Log.d("NOTICE", subject);
//                    String content = object.getString("content");
//                    Log.d("NOTICE", content);
//                    boolean priority = object.getBoolean("priority");
//                    Log.d("NOTICE", String.valueOf(priority));
//
//                    NoticeModel noticeModel = new NoticeModel(id, subject, content, priority);
//                    noticeModelList.add(noticeModel);
//                }
//                callAdapter(noticeModelList);
//            }
//        });
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(Notices.this, SendNotice.class));
//            }
//        });
//    }
//}

//public class Stock extends AppCompatActivity {
//
//    ImageView image;
//    TextView id;
//    TextView name;
//    TextView description;
//    TextView extra1;
//    TextView extra2;
//    TextView price;
//    TextView stock;
//    Button stock_button;
//    String productId;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_stock);
//
//        name = findViewById(R.id.stock);
//
//        productId = getIntent().getStringExtra("id");
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("id", productId);
//        ParseCloud.callFunctionInBackground("stock", params, new FunctionCallback<ArrayList>() {
//            public void done(ArrayList response, ParseException e) {
//                if (e == null) {
//                    Log.d("RESPONSE", response.toString());
//                    JSONArray jsArray = new JSONArray(response);
//                    Log.d("newJSON", jsArray.toString());
//                    HashMap<String, String> stores = new HashMap<>();
//                    try {
//                        for (int i = 0; i < jsArray.length(); i++) {
//                            JSONObject obj = jsArray.getJSONObject(i);
//
//                            Iterator<String> keys= obj.keys();
//                            while (keys.hasNext())
//                            {
//                                String keyValue = (String)keys.next();
//                                Log.d("store_name", keyValue);
////                                String valueString = obj.getString(keyValue);
////                                Log.d("_____TEST_____", valueString);
//                                String stockLevel = obj.getString(keyValue);
//                                Log.d("stock_level", stockLevel);
//                                stores.put(keyValue, stockLevel);
//                            }
//                            Log.d("FINISHED_LIST", String.valueOf(stores));
//
////                            String id = obj.getString("id");
////                            String name = obj.getString("name");
////                            String description = obj.getString("description");
////                            String extra1 = obj.getString("extra1");
////                            String extra2 = obj.getString("extra2");
////                            int price = 0;
////                            int stock = 0;
////                            String image = obj.getString("image");
////
////                            ProductModel productModel = new ProductModel(id, name, description, extra1, extra2, price, stock, image);
////                            productModelList.add(productModel);
//                        }
////                        callAdapter(productModelList);
////                        Log.d("listArray", productModelList.toString());
//                    } catch (JSONException ex) {
//                        Log.e("JsonParserIn", "Exception", ex);
//                    }
//                } else {
//                    Log.e("cloudCode", e.toString());
//                }
//            }
//        });
//    }
//}
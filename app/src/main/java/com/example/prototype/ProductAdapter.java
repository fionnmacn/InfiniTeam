package com.example.prototype;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {

    Context context;
    ArrayList<Product> arrayList;

    public ProductAdapter(Context context, ArrayList<Product> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public ProductAdapter(ArrayList<Product> arrayList) {
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list, null);

        // Places the product's id and name in each row
        TextView textView1 = convertView.findViewById(R.id.id);
        TextView textView2 = convertView.findViewById(R.id.name);

        Product product = arrayList.get(position);
        textView1.setText(Product.get_id());
        textView2.setText(Product.get_name());

        return convertView;
    }

    @Override
    public int getCount() {
        return 1;
    }
}
package com.example.InfiniTeam;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductAdapterVh> implements Filterable {

    private List<ProductModel> productModelList;
    private List<ProductModel> getProductModelListFiltered;
    private Context context;
    private SelectedProduct selectedProduct;

    public ProductAdapter(List<ProductModel> productModelList, SelectedProduct selectedProduct) {
        this.productModelList = productModelList;
        this.getProductModelListFiltered = productModelList;
        this.selectedProduct = selectedProduct;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ProductAdapterVh(LayoutInflater.from(context).inflate(R.layout.products_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductAdapterVh holder, int position) {

        ProductModel productModel = productModelList.get(position);

        String id = productModel.getId();
        String name = productModel.getName();
        String description = productModel.getDescription();
        String image = productModel.getImage();

        holder.tvId.append(id);
        holder.tvName.setText(name);
        holder.tvDescription.setText(description);

//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Images");
//        query.whereEqualTo("product_id", id);
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if(e==null){
//                    for(ParseObject object : objects){
//                        Log.i("info", "image found!");
//                        ParseFile file = (ParseFile) object.get("image");
//                        file.getDataInBackground(new GetDataCallback() {
//                            @Override
//                            public void done(byte[] data, ParseException e) {
//                                if(e==null){
//                                    Bitmap bitmapImage = BitmapFactory.decodeByteArray(data, 0, data.length);
//                                    holder.ivImage.setImageBitmap(bitmapImage);
//                                }
//                                else{
//                                    Log.i("info", e.getMessage());
//                                }
//                            }
//                        });
//                    }
//                }
//                else{
//                    Log.i("info", e.getMessage());
//                }
//            }
//        });

//        String uri = "@drawable/" + id;
//        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
//        Drawable res = ContextCompat.getDrawable(context, imageResource);
//        if (res != null) {
//            Log.d("IMAGE", String.valueOf(imageResource));
//            Log.d("IMAGE", String.valueOf(res));
//            holder.ivImage.setImageDrawable(res);
//        }
    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null | constraint.length() == 0) {
                    filterResults.count = getProductModelListFiltered.size();
                    filterResults.values = getProductModelListFiltered;
                }
                else {
                    String searched = constraint.toString().toLowerCase();
                    List<ProductModel> resultData = new ArrayList<>();

                    for (ProductModel productModel: getProductModelListFiltered) {
                        if (productModel.getName().toLowerCase().contains(searched)
                                | productModel.getDescription().toLowerCase().contains(searched)) {
                            resultData.add(productModel);
                        }
                    }

                    filterResults.count = resultData.size();
                    filterResults.values = resultData;

                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                productModelList = (List<ProductModel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }


    public interface SelectedProduct{
        void selectedProduct(ProductModel productModel);
    }

    public class ProductAdapterVh extends RecyclerView.ViewHolder {

        TextView tvId;
        TextView tvName;
        TextView tvDescription;
        ImageView ivImage;

        public ProductAdapterVh(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.product_id);
            tvName = itemView.findViewById(R.id.product_name);
            tvDescription = itemView.findViewById(R.id.product_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedProduct.selectedProduct(productModelList.get(getAdapterPosition()));
                }
            });
        }
    }
}

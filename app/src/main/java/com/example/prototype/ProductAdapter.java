package com.example.prototype;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        this. selectedProduct = selectedProduct;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ProductAdapterVh(LayoutInflater.from(context).inflate(R.layout.products_list, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductAdapterVh holder, int position) {

        ProductModel productModel = productModelList.get(position);

        String id = productModel.getId();
        String name = productModel.getName();
        String description = productModel.getDescription();

        holder.tvId.setText(id);
        holder.tvName.setText(name);
        holder.tvDescription.setText(description);
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

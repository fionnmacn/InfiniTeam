package com.example.InfiniTeam;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockAdapterVh> implements Filterable {

    private List<StockModel> stockModelList;
    private List<StockModel> getStockModelListFiltered;
    private Context context;
    private SelectedStore selectedStore;

    public StockAdapter(List<StockModel> stockModelList, SelectedStore selectedStore) {
        this.stockModelList = stockModelList;
        this.getStockModelListFiltered = stockModelList;
        this.selectedStore = selectedStore;
    }

    @NonNull
    @Override
    public StockAdapter.StockAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new StockAdapterVh(LayoutInflater.from(context).inflate(R.layout.stock_list, null));
    }

    @Override
    public void onBindViewHolder(@NonNull StockAdapter.StockAdapterVh holder, int position) {

        StockModel stockModel = stockModelList.get(position);

        String store = stockModel.getStore();
        String stock = String.valueOf(stockModel.getStock());
        if (stock != null) {
            Log.d("STOCK_ADAPTER", String.valueOf(stock));
        }

        holder.tvStore.setText(store);
        holder.tvStock.setText(stock);
        if (stock == "0") {
            holder.ivEmpty.setVisibility(View.VISIBLE);
        } else {
            holder.ivEmpty.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return stockModelList.size();
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null | constraint.length() == 0) {
                    filterResults.count = getStockModelListFiltered.size();
                    filterResults.values = getStockModelListFiltered;
                }
                else {
                    String searched = constraint.toString().toLowerCase();
                    List<StockModel> resultData = new ArrayList<>();

                    for (StockModel stockModel: getStockModelListFiltered) {
                        if (stockModel.getStore().toLowerCase().contains(searched)) {
                            resultData.add(stockModel);
                        }
                    }

                    filterResults.count = resultData.size();
                    filterResults.values = resultData;

                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                stockModelList = (List<StockModel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }


    public interface SelectedStore {
        void selectedStore(StockModel stockModel);
    }

    public class StockAdapterVh extends RecyclerView.ViewHolder {

        TextView tvStore;
        TextView tvStock;
        ImageView ivEmpty;

        public StockAdapterVh(@NonNull View itemView) {
            super(itemView);
            tvStore = itemView.findViewById(R.id.stock_store);
            tvStock = itemView.findViewById(R.id.stock_level);
            ivEmpty = itemView.findViewById(R.id.stock_empty);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedStore.selectedStore(stockModelList.get(getAdapterPosition()));
                }
            });
        }
    }
}

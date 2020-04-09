package com.example.spyonlineuk.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spyonlineuk.R;
import com.example.spyonlineuk.models.Products;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecentlyAddedAdapter extends RecyclerView.Adapter<RecentlyAddedAdapter.RecentlyAddedHolder> {
    private ArrayList<Products> datasets;
    private AdapterView.OnItemClickListener onItemClickListener;

    public RecentlyAddedAdapter(ArrayList<Products> datasets, AdapterView.OnItemClickListener onItemClickListener) {
        this.datasets = datasets;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecentlyAddedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layot_recently_added,parent,false);

        return new RecentlyAddedHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecentlyAddedHolder holder, int position) {
        Picasso.with(holder.itemView.getContext()).load(datasets.get(position).getProductImage()).into(holder.ivProduct);
        holder.tvProductName.setText(datasets.get(position).getProductName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(null,holder.itemView,holder.getAdapterPosition(),0);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datasets.size();
    }

    public class RecentlyAddedHolder extends RecyclerView.ViewHolder {

        TextView tvProductName;
        TextView tvProductPrice;
        ImageView ivProduct;
        TextView tvProductDetail;
        TextView tvProductDiscount;
        public RecentlyAddedHolder(@NonNull View itemView) {
            super(itemView);

            tvProductName=itemView.findViewById(R.id.tvProductName);
            ivProduct=itemView.findViewById(R.id.ivProductsImage);

        }
    }
}

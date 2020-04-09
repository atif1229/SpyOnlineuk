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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsHolder> {
    private ArrayList<Products> dataset;
    private AdapterView.OnItemClickListener onItemClickListener;
    private AdapterView.OnItemClickListener favoritesClickListener;

    public ProductsAdapter(ArrayList<Products> dataset, AdapterView.OnItemClickListener onItemClickListener, AdapterView.OnItemClickListener favorites) {
        this.dataset = dataset;
        this.onItemClickListener = onItemClickListener;
        this.favoritesClickListener = favorites;
    }

    @NonNull
    @Override
    public ProductsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_products, parent, false);
        return new ProductsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductsHolder holder, int position) {
        // Picasso.get().load(dataset.get(position).getProductImage()).into(holder.ivProdImage);
        Picasso.with(holder.itemView.getContext()).load(dataset.get(position).getProductImage()).into(holder.ivProdImage);
        holder.tvProdName.setText(dataset.get(position).getProductName());
        NumberFormat nf = new DecimalFormat("#,###");
        holder.tvProdPrice.setText("$" + nf.format(dataset.get(position).getProductPrice()) + ".00");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(null, holder.itemView, holder.getAdapterPosition(), 0);
            }
        });
        holder.ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoritesClickListener.onItemClick(null, holder.ivFavorite, holder.getAdapterPosition(), 0);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ProductsHolder extends RecyclerView.ViewHolder {
        ImageView ivProdImage;
        TextView tvProdName;
        TextView tvProdPrice;
        ImageView ivFavorite;

        public ProductsHolder(@NonNull View itemView) {
            super(itemView);

            ivProdImage = itemView.findViewById(R.id.ivProdImage);
            tvProdName = itemView.findViewById(R.id.tvProdName);
            tvProdPrice = itemView.findViewById(R.id.tvProdPrice);
            ivFavorite = itemView.findViewById(R.id.ivFavorite);
        }
    }
}

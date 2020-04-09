package com.example.spyonlineuk.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spyonlineuk.R;
import com.example.spyonlineuk.models.CartItem;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder>{
    private ArrayList<CartItem> dataset;
    private AdapterView.OnItemClickListener incrementClickListener;
    private AdapterView.OnItemClickListener decrementClickListener;
    private AdapterView.OnItemClickListener removeClickListener;

    public CartAdapter(ArrayList<CartItem> dataset, AdapterView.OnItemClickListener incrementClickListener, AdapterView.OnItemClickListener decrementClickListener, AdapterView.OnItemClickListener removeClickListener) {
        this.dataset = dataset;
        this.incrementClickListener = incrementClickListener;
        this.decrementClickListener = decrementClickListener;
        this.removeClickListener = removeClickListener;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_cart_items,parent,false);
        return new CartHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartHolder holder, int position) {
        Picasso.with(holder.itemView.getContext()).load(dataset.get(position).getProducts().getProductImage()).into(holder.ivProductImage);
        holder.tvProductName.setText(dataset.get(position).getProducts().getProductName());
        holder.tvProductSize.setText("Size: "+dataset.get(position).getSelectedSizes().getSizeName());
        holder.tvQuantity.setText(String.valueOf(dataset.get(position).getQuantity()));

        int price=dataset.get(position).getProducts().getProductPrice();
        int quantity=dataset.get(position).getQuantity();
        int amount=price*quantity;
        NumberFormat nf=new DecimalFormat("#,###");
        holder.tvAmount.setText("$. "+String.valueOf(nf.format(amount)));

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementClickListener.onItemClick(null,holder.btnAdd,holder.getAdapterPosition(),0);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrementClickListener.onItemClick(null,holder.btnDelete,holder.getAdapterPosition(),0);
            }
        });
        holder.tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeClickListener.onItemClick(null,holder.tvRemove,holder.getAdapterPosition(),0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder {
        ImageView ivProductImage;
        TextView tvProductName;
        TextView tvProductPrice;
        TextView tvProductSize;
        ImageButton tvRemove;
        TextView tvAmount;
        ImageButton btnAdd;
        ImageButton btnDelete;
        TextView tvQuantity;
        public CartHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage=itemView.findViewById(R.id.ivProductImage);
            tvProductName=itemView.findViewById(R.id.tvProductName);
            tvProductPrice=itemView.findViewById(R.id.tvProdPrice);
            tvProductSize=itemView.findViewById(R.id.tvProductSize);
            tvRemove=itemView.findViewById(R.id.tvRemove);
            tvAmount=itemView.findViewById(R.id.tvAmount);
            btnAdd=itemView.findViewById(R.id.btnAdd);
            tvQuantity=itemView.findViewById(R.id.tvQuantity);
            btnDelete=itemView.findViewById(R.id.btnDelete);


        }
    }
}

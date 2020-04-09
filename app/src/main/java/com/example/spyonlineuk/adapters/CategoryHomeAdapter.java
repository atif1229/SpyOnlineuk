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
import com.example.spyonlineuk.models.Categories;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryHomeAdapter extends RecyclerView.Adapter<CategoryHomeAdapter.CategoryHolder> {
    private ArrayList<Categories> dataset;
    private AdapterView.OnItemClickListener onItemClickListener;

    public CategoryHomeAdapter(ArrayList<Categories> dataset, AdapterView.OnItemClickListener onItemClickListener) {
        this.dataset = dataset;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override

    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_home_fragment,parent,false);
        return new CategoryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryHolder holder, int position) {
        Picasso.with(holder.itemView.getContext()).load(dataset.get(position).getCatImage()).into(holder.ivCategory);
        holder.tvCategory.setText(dataset.get(position).getCatName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(null,holder.itemView,holder.getAdapterPosition(),0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {
        ImageView ivCategory;
        TextView tvCategory;

        public CategoryHolder(@NonNull View itemView) {

            super(itemView);
            ivCategory=itemView.findViewById(R.id.ivCategory);
            tvCategory=itemView.findViewById(R.id.tvCategory);
        }
    }
}

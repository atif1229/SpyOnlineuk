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
import com.example.spyonlineuk.activities.MainActivity;
import com.example.spyonlineuk.models.Categories;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {
    private ArrayList<Categories> dataset;
    private AdapterView.OnItemClickListener onItemClickListener;

    public CategoryAdapter(ArrayList<Categories> dataset, AdapterView.OnItemClickListener onItemClickListener) {
        this.dataset = dataset;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override

    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_category,parent,false);
        return new CategoryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryHolder holder, int position) {
        Picasso.with(holder.itemView.getContext()).load(dataset.get(position).getCatImage()).into(holder.ivCatImage);
        holder.tvCatName.setText(dataset.get(position).getCatName());
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
        ImageView ivCatImage;
        TextView tvCatName;
        TextView tvCaption;

        public CategoryHolder(@NonNull View itemView) {

            super(itemView);
            ivCatImage=itemView.findViewById(R.id.ivCatImage);
            tvCatName=itemView.findViewById(R.id.tvCatName);
        }
    }
}

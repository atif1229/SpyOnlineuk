package com.example.spyonlineuk.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spyonlineuk.R;
import com.example.spyonlineuk.models.Sizes;

import java.util.ArrayList;

public class SizesAdapter extends RecyclerView.Adapter<SizesAdapter.SizesHolder> {
    private ArrayList<Sizes> dataset;
    private int selectedPosition;

    public SizesAdapter(ArrayList<Sizes> dataset) {
        this.dataset = dataset;
        selectedPosition = 0;
    }

    @NonNull
    @Override
    public SizesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_sizes, parent, false);

        return new SizesHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final SizesHolder holder, final int position) {
        holder.tvSizesName.setText(dataset.get(position).getSizeName());
        if (dataset.get(position).getSizeStatus() == 0) {
            holder.tvSizesName.setEnabled(false);
        } else {
            holder.tvSizesName.setEnabled(true);
        }
        if (dataset.get(position).isSelected()) {
            holder.tvSizesName.setSelected(true);
        } else {
            holder.tvSizesName.setSelected(false);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    selectedPosition = holder.getAdapterPosition();
                    if (dataset.get(holder.getAdapterPosition()).getSizeStatus() == 0) {
                        return;
                    }
                    selectedPosition = holder.getAdapterPosition();

                    for (int i = 0; i < dataset.size(); i++) {
                        if (i == holder.getAdapterPosition()) {
                            dataset.get(i).setSelected(true);

                        } else {
                            dataset.get(i).setSelected(false);
                        }
                    }

                notifyDataSetChanged();
            }
        });
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class SizesHolder extends RecyclerView.ViewHolder {
        TextView tvSizesName;

        public SizesHolder(@NonNull View itemView) {
            super(itemView);
            tvSizesName = itemView.findViewById(R.id.tvSizeName);
        }
    }
}

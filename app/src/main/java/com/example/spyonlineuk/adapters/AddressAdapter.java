package com.example.spyonlineuk.adapters;

import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spyonlineuk.R;
import com.example.spyonlineuk.models.Addresses;

import java.util.ArrayList;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressHolder> {
    private ArrayList<Addresses> dataset;
    private int selectedAddress;

    public AddressAdapter(ArrayList<Addresses> dataset) {
        this.dataset = dataset;
        selectedAddress = -1;
    }

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_addresses, parent, false);
        return new AddressHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final AddressHolder holder, int position) {
        holder.tvAddress.setText(dataset.get(position).getFullAddress());
        holder.tvNearByLandMark.setText(dataset.get(position).getNearByLandMark());

        if (position==selectedAddress){
            holder.rdoAddressSelection.setChecked(true);
        }
       else
       {
           holder.rdoAddressSelection.setChecked(false);
       }


        /*if (position == selectedAddress && holder.rdoAddressSelection.isChecked()) {

            holder.rdoAddressSelection.setChecked(false);

        } else if (position == selectedAddress) {

            holder.rdoAddressSelection.setChecked(true);
        } else {
            holder.rdoAddressSelection.setChecked(true);

        }*/

        holder.rdoAddressSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.rdoAddressSelection.isChecked()) {

                    selectedAddress = holder.getAdapterPosition();
                    notifyDataSetChanged();
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.getAdapterPosition()==-1){
                    return;
                }
                selectedAddress = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public int getSelectedAddress() {
        return selectedAddress;
    }

    public class AddressHolder extends RecyclerView.ViewHolder {
        RadioButton rdoAddressSelection;
        TextView tvAddress;
        TextView tvNearByLandMark;

        public AddressHolder(@NonNull View itemView) {
            super(itemView);
            rdoAddressSelection = itemView.findViewById(R.id.rdoAddressSelection);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvNearByLandMark = itemView.findViewById(R.id.tvNearByLandmark);

        }
    }
}

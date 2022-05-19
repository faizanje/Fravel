package com.example.fravell.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fravell.Models.CustomOrder;
import com.example.fravell.Models.OrderStatus;
import com.example.fravell.databinding.ItemRvCustomOrderBinding;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;


public class CustomOrderAdapter extends RecyclerView.Adapter<CustomOrderAdapter.MyViewHolder> {
    ArrayList<CustomOrder> customOrderArrayList;
    Context context;
    OnCustomOrderClickListener onCustomOrderClickListener;

    public CustomOrderAdapter(Context context, ArrayList<CustomOrder> customOrderArrayList) {
        this.context = context;
        this.customOrderArrayList = customOrderArrayList;
    }

    public void setOnCustomOrderClickListener(OnCustomOrderClickListener onCustomOrderClickListener) {
        this.onCustomOrderClickListener = onCustomOrderClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRvCustomOrderBinding binding = ItemRvCustomOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        CustomOrder order = customOrderArrayList.get(holder.getAbsoluteAdapterPosition());
        holder.binding.tvTitle.setText("Order # " + (int) System.currentTimeMillis());
        holder.binding.tvFrom.setText(order.getFravellerCriteria().getFromCity());
        holder.binding.tvTo.setText(order.getFravellerCriteria().getToCity());
        holder.binding.tvFraveller.setText(order.getAvailableFraveller().getName());
        holder.binding.tvStatus.setText(order.getOrderStatus());
        if(order.getOrderStatus().equals(OrderStatus.PROCESSING.getStatus())){
            holder.binding.btnDelivered.setVisibility(View.VISIBLE);
            holder.binding.btnDelivered.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onCustomOrderClickListener != null){
                        onCustomOrderClickListener.onCustomOrderClicked(position,order);
                    }
                }
            });
        }


    }


    @Override
    public int getItemCount() {
        return customOrderArrayList.size();
    }

    public interface OnCustomOrderClickListener {
        void onCustomOrderClicked(int position, CustomOrder order);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemRvCustomOrderBinding binding;

        public MyViewHolder(@NotNull ItemRvCustomOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }
}




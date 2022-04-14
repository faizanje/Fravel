package com.example.fravell.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fravell.Models.CartItem;
import com.example.fravell.Models.Order;
import com.example.fravell.Utils.Constants;
import com.example.fravell.Utils.DateTimeUtils;
import com.example.fravell.Utils.NumberUtils;
import com.example.fravell.databinding.ItemRvOrderBinding;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    ArrayList<Order> orderArrayList;
    Context context;
    OnOrderClickListener onOrderClickListener;

    public OrderAdapter(Context context, ArrayList<Order> orderArrayList) {
        this.context = context;
        this.orderArrayList = orderArrayList;
    }

    public void setOnOrderClickListener(OnOrderClickListener onOrderClickListener) {
        this.onOrderClickListener = onOrderClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRvOrderBinding binding = ItemRvOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        Order order = orderArrayList.get(holder.getAbsoluteAdapterPosition());

        int quantity = 0;
        double totalAmount = 7;
        for (CartItem cartItem : order.getCartItemArrayList()) {
            quantity += cartItem.getQuantity();
        }
        holder.binding.tvDate.setText(DateTimeUtils.millisecondsToDate(order.getTimeInMillis(), Constants.DATE_FORMAT));
        holder.binding.tvTitle.setText("Order # " + order.getOrderId());
        holder.binding.tvQuantity.setText("" + quantity);
        holder.binding.tvAmount.setText("$" + NumberUtils.round(order.getTotalAmount(), 1));
        holder.binding.tvStatus.setText(order.getOrderStatus());

        holder.binding.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onOrderClickListener != null){
                    onOrderClickListener.onOrderClicked(holder.getAbsoluteAdapterPosition(),order);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public interface OnOrderClickListener {
        void onOrderClicked(int position, Order order);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemRvOrderBinding binding;

        public MyViewHolder(@NotNull ItemRvOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }
}




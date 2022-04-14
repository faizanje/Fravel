package com.example.fravell.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fravell.Models.CartItem;
import com.example.fravell.Utils.NumberUtils;
import com.example.fravell.databinding.ItemRvOrderedProductBinding;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;


public class OrderedProductAdapter extends RecyclerView.Adapter<OrderedProductAdapter.MyViewHolder> {
    ArrayList<CartItem> cartItemArrayList;
    Context context;
    OnCartItemClickListener onCartItemClickListener;

    public OrderedProductAdapter(Context context, ArrayList<CartItem> cartItemArrayList) {
        this.context = context;
        this.cartItemArrayList = cartItemArrayList;
    }

    public void setOnCartItemClickListener(OnCartItemClickListener onCartItemClickListener) {
        this.onCartItemClickListener = onCartItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRvOrderedProductBinding binding = ItemRvOrderedProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        CartItem cartItem = cartItemArrayList.get(holder.getAbsoluteAdapterPosition());

        holder.binding.tvTitle.setText(cartItem.getProduct().getTitle());
        holder.binding.tvManufacturer.setText(cartItem.getProduct().getManufacturer());
        holder.binding.tvVariant.setText("Size: " + cartItem.getVariant());
        holder.binding.tvUnit.setText("Unit(s): " + cartItem.getQuantity());

        Glide.with(context)
                .load(cartItem.getProduct().getImage())
                .into(holder.binding.ivProduct);

        double discountedPrice = NumberUtils.getDiscountedPrice(cartItem.getProduct().getPrice());
        holder.binding.tvPrice.setText("$" + NumberUtils.round(discountedPrice, 1));

        if(cartItem.hasFeedbackLeft()){
//            holder.binding.btnGiveFeedback.setVisibility(View.GONE);
            holder.binding.btnGiveFeedback.setEnabled(false);
            holder.binding.btnGiveFeedback.setText("Reviewed");

        }

        holder.binding.btnGiveFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCartItemClickListener != null) {
                    onCartItemClickListener.onCartItemClicked(holder.getAbsoluteAdapterPosition(), cartItem);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return cartItemArrayList.size();
    }

    public interface OnCartItemClickListener {
        void onCartItemClicked(int position, CartItem cartItem);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemRvOrderedProductBinding binding;

        public MyViewHolder(@NotNull ItemRvOrderedProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }
}




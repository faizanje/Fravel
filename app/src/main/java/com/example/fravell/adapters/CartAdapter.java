package com.example.fravell.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fravell.Models.CartItem;
import com.example.fravell.Utils.Constants;
import com.example.fravell.Utils.NumberUtils;
import com.example.fravell.databinding.ItemRvCartBinding;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    ArrayList<CartItem> cartItemArrayList;
    Context context;
    OnCartItemClickListener onCartItemClickListener;

    public CartAdapter(Context context, ArrayList<CartItem> cartItemArrayList) {
        this.context = context;
        this.cartItemArrayList = cartItemArrayList;
    }

    public void setOnCartItemClickListener(OnCartItemClickListener onCartItemClickListener) {
        this.onCartItemClickListener = onCartItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRvCartBinding binding = ItemRvCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        CartItem cartItem = cartItemArrayList.get(holder.getAdapterPosition());
        holder.binding.tvTitle.setText(cartItem.getProduct().getTitle());

        double discountedPrice = NumberUtils.getDiscountedPrice(cartItem.getProduct().getPrice()) * cartItem.getQuantity();

//        holder.binding.tvPrice.setText("$" + NumberUtils.round(discountedPrice, 1));


        Glide.with(context)
                .load(cartItem.getProduct().getImage())
                .into(holder.binding.ivProduct);

        holder.binding.tvQuantity.setText(cartItem.getQuantity() + "");
        holder.binding.tvPrice.setText("$" +NumberUtils.round(discountedPrice,1));
        holder.binding.tvVariant.setText(cartItem.getVariant());
        holder.binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCartItemClickListener != null) {
                    if (cartItem.getQuantity() <= Constants.MAX_QUANTITY) {
                        onCartItemClickListener.onIncrementQuantityClicked(holder.getAdapterPosition(), cartItem);
                    } else {
                        Toast.makeText(context, "You can order a max of " + Constants.KEY_PRODUCT + " items", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        holder.binding.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCartItemClickListener != null) {
                    if (cartItem.getQuantity() > 1) {
                        onCartItemClickListener.onDecrementQuantityClicked(holder.getAdapterPosition(), cartItem);
                    } else {
                        onCartItemClickListener.onRemoveCartItemClicked(holder.getAdapterPosition(), cartItem);
                    }
                }
            }
        });


        holder.binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCartItemClickListener != null) {
                    onCartItemClickListener.onRemoveCartItemClicked(holder.getAdapterPosition(), cartItem);
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

        void onRemoveCartItemClicked(int position, CartItem cartItem);

        void onIncrementQuantityClicked(int position, CartItem cartItem);

        void onDecrementQuantityClicked(int position, CartItem cartItem);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemRvCartBinding binding;

        public MyViewHolder(@NotNull ItemRvCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }
}




package com.example.fravell.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fravell.Models.Product;
import com.example.fravell.Utils.NumberUtils;
import com.example.fravell.databinding.ItemRvProductBinding;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;


public class ProductsHorizontalAdapter extends RecyclerView.Adapter<ProductsHorizontalAdapter.MyViewHolder> {

    ArrayList<Product> productArrayList;
    Context context;
    OnProductClickListener onProductClickListener;

    public ProductsHorizontalAdapter(Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }

    public void setOnProductClickListener(OnProductClickListener onProductClickListener) {
        this.onProductClickListener = onProductClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRvProductBinding binding = ItemRvProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        Product product = productArrayList.get(holder.getAdapterPosition());
        holder.binding.tvProductName.setText(product.getTitle());
        holder.binding.tvManufacturer.setText(product.getManufacturer());

        holder.binding.tvPrice.setText("$" + NumberUtils.round(product.getPrice(), 1));
        holder.binding.tvPrice.setPaintFlags(holder.binding.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        double discountedPrice = NumberUtils.getDiscountedPrice(product.getPrice());
        holder.binding.tvDiscountedPrice.setText("$" + NumberUtils.round(discountedPrice, 1));

        Glide.with(context)
                .load(product.getImage())
                .into(holder.binding.ivImage);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onProductClickListener != null) {
                    onProductClickListener.onProductsClicked(holder.getAdapterPosition(), product);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public interface OnProductClickListener {
        void onProductsClicked(int position, Product product);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemRvProductBinding binding;

        public MyViewHolder(@NotNull ItemRvProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
package com.example.fravell.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fravell.Models.Category;
import com.example.fravell.databinding.ItemRvCategoryBinding;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;


public class CategoriesHorizontalAdapter extends RecyclerView.Adapter<CategoriesHorizontalAdapter.MyViewHolder> {
    ArrayList<Category> categoryArrayList;
    Context context;
    OnCategoryClickListener onCategoryClickListener;

    public CategoriesHorizontalAdapter(Context context, ArrayList<Category> categoryArrayList) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
    }

    public void setOnCategoryClickListener(OnCategoryClickListener onCategoryClickListener) {
        this.onCategoryClickListener = onCategoryClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRvCategoryBinding binding = ItemRvCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        Category category = categoryArrayList.get(holder.getAdapterPosition());
        holder.binding.tvName.setText(category.getCategoryName());
        Glide.with(context)
                .load(category.getCategoryImageUrl())
                .into(holder.binding.ivImage);

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onCategoryClickListener != null){
                    onCategoryClickListener.onCategoryClicked(holder.getAdapterPosition(), category);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public interface OnCategoryClickListener {
        void onCategoryClicked(int position, Category category);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemRvCategoryBinding binding;

        public MyViewHolder(@NotNull ItemRvCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }
}




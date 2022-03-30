package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Models.Category;
import com.example.fravell.databinding.ItemRvCategory2Binding;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;


public class CategoriesVerticalAdapter extends RecyclerView.Adapter<CategoriesVerticalAdapter.MyViewHolder> {
    ArrayList<Category> categoryArrayList;
    Context context;
    OnCategoryClickListener onCategoryClickListener;

    public CategoriesVerticalAdapter(Context context, ArrayList<Category> categoryArrayList) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
    }

    public void setOnCategoryClickListener(OnCategoryClickListener onCategoryClickListener) {
        this.onCategoryClickListener = onCategoryClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRvCategory2Binding binding = ItemRvCategory2Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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
                if (onCategoryClickListener != null) {
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
        ItemRvCategory2Binding binding;

        public MyViewHolder(@NotNull ItemRvCategory2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }
}




package com.example.fravell.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fravell.Models.Review;
import com.example.fravell.databinding.ItemRvReviewBinding;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {
    ArrayList<Review> reviewArrayList;
    Context context;
    OnReviewClickListener onReviewClickListener;

    public ReviewAdapter(Context context, ArrayList<Review> reviewArrayList) {
        this.context = context;
        this.reviewArrayList = reviewArrayList;
    }

    public void setOnReviewClickListener(OnReviewClickListener onReviewClickListener) {
        this.onReviewClickListener = onReviewClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRvReviewBinding binding = ItemRvReviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        Review review = reviewArrayList.get(holder.getAbsoluteAdapterPosition());
        if (review.getImageUrl() != null) {
            holder.binding.layoutImage.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(review.getImageUrl())
                    .into(holder.binding.ivImage);
        }
        holder.binding.ratingBar.setRating(review.getRating());
        holder.binding.tvUsername.setText(review.getUserName());
        holder.binding.tvReview.setText(review.getReview());
    }


    @Override
    public int getItemCount() {
        return reviewArrayList.size();
    }

    public interface OnReviewClickListener {
        void onReviewClicked(int position, Review review);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemRvReviewBinding binding;

        public MyViewHolder(@NotNull ItemRvReviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }
}




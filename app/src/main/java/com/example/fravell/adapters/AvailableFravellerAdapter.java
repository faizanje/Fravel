package com.example.fravell.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fravell.Models.AvailableFraveller;
import com.example.fravell.Utils.DateTimeUtils;
import com.example.fravell.databinding.ItemRvAvailableFravellerBinding;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;


public class AvailableFravellerAdapter extends RecyclerView.Adapter<AvailableFravellerAdapter.MyViewHolder> {
    ArrayList<AvailableFraveller> availableFravellerArrayList;
    Context context;
    OnAvailableFravellerClickListener onAvailableFravellerClickListener;

    public AvailableFravellerAdapter(Context context, ArrayList<AvailableFraveller> availableFravellerArrayList) {
        this.context = context;
        this.availableFravellerArrayList = availableFravellerArrayList;
    }

    public void setOnAvailableFravellerClickListener(OnAvailableFravellerClickListener onAvailableFravellerClickListener) {
        this.onAvailableFravellerClickListener = onAvailableFravellerClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRvAvailableFravellerBinding binding = ItemRvAvailableFravellerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        AvailableFraveller availableFraveller = availableFravellerArrayList.get(holder.getAbsoluteAdapterPosition());
        holder.binding.tvUsername.setText(availableFraveller.getName());
        holder.binding.tvStartDate.setText(DateTimeUtils.millisecondsToDate(availableFraveller.getFromDateInMillis()));
        holder.binding.tvEndDate.setText(DateTimeUtils.millisecondsToDate(availableFraveller.getToDateInMillis()));
    }


    @Override
    public int getItemCount() {
        return availableFravellerArrayList.size();
    }

    public interface OnAvailableFravellerClickListener {
        void onAvailableFravellerClicked(int position, AvailableFraveller availableFraveller);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemRvAvailableFravellerBinding binding;

        public MyViewHolder(@NotNull ItemRvAvailableFravellerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }
}




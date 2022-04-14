package com.example.fravell.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fravell.Models.Order;
import com.example.fravell.Models.OrderStatus;
import com.example.fravell.OrderDetailActivity;
import com.example.fravell.Utils.Constants;
import com.example.fravell.Utils.FirebaseUtils;
import com.example.fravell.adapters.OrderAdapter;
import com.example.fravell.databinding.FragmentProcessingOrdersBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ProcessingOrdersFragment extends Fragment {
    FragmentProcessingOrdersBinding binding;
    OrderAdapter adapter;
    ArrayList<Order> orderArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProcessingOrdersBinding.inflate(getLayoutInflater());

        adapter = new OrderAdapter(requireContext(), orderArrayList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnOrderClickListener(new OrderAdapter.OnOrderClickListener() {
            @Override
            public void onOrderClicked(int position, Order order) {
                Intent intent = new Intent(requireContext(), OrderDetailActivity.class);
                intent.putExtra(Constants.KEY_ORDER,order);
                startActivity(intent);
            }
        });
        binding.swipeRefreshLayout.setRefreshing(true);
        getData();
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        return binding.getRoot();
    }

    private void getData() {
        FirebaseUtils.getOrdersReference()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        binding.swipeRefreshLayout.setRefreshing(false);
                        orderArrayList.clear();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            Order order = child.getValue(Order.class);
                            if(order != null){
                                if (order.getOrderStatus().equals(OrderStatus.PROCESSING.getStatus())) {
                                    orderArrayList.add(order);
                                }

                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        binding.swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
package com.example.fravell.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fravell.Models.CustomOrder;
import com.example.fravell.Models.OrderStatus;
import com.example.fravell.Utils.FirebaseUtils;
import com.example.fravell.adapters.CustomOrderAdapter;
import com.example.fravell.databinding.FragmentCustomInProgressBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomCompletedFragment extends Fragment {
    FragmentCustomInProgressBinding binding;
    CustomOrderAdapter adapter;
    ArrayList<CustomOrder> customOrderArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCustomInProgressBinding.inflate(getLayoutInflater());

        adapter = new CustomOrderAdapter(requireContext(), customOrderArrayList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnCustomOrderClickListener(new CustomOrderAdapter.OnCustomOrderClickListener() {
            @Override
            public void onCustomOrderClicked(int position, CustomOrder order) {
//                Intent intent = new Intent(requireContext(), OrderDetailActivity.class);
//                intent.putExtra(Constants.KEY_ORDER,order);
//                startActivity(intent);
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
        FirebaseUtils.getCustomOrdersReference()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        binding.swipeRefreshLayout.setRefreshing(false);
                        customOrderArrayList.clear();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            CustomOrder customOrder = child.getValue(CustomOrder.class);
                            if (customOrder != null) {
                                if (customOrder.getOrderStatus().equals(OrderStatus.DELIVERED.getStatus())) {
                                    customOrderArrayList.add(customOrder);
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

}
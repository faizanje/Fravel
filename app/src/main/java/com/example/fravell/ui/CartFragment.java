package com.example.fravell.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fravell.Models.CartItem;
import com.example.fravell.Utils.Constants;
import com.example.fravell.adapters.CartAdapter;
import com.example.fravell.databinding.FragmentCartBinding;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    FragmentCartBinding binding;
    Button checkout;
    LinearLayout home, messages, cart, account;
    CartAdapter adapter;
    ArrayList<CartItem> cartItemArrayList = new ArrayList<>();
    int deliveryPrice = Constants.DELIVERY_PRICE;
    int orderAmount = 0;
    int totalAmount = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
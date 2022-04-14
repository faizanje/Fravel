package com.example.fravell.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fravell.AllCategoriesActivity;
import com.example.fravell.AllProductsByCategoryActivity;
import com.example.fravell.Models.Category;
import com.example.fravell.Models.Product;
import com.example.fravell.ProductDetailPage;
import com.example.fravell.Utils.Constants;
import com.example.fravell.adapters.CategoriesHorizontalAdapter;
import com.example.fravell.adapters.ProductsHorizontalAdapter;
import com.example.fravell.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    LinearLayout home, messages, cart, account;
    ProductsHorizontalAdapter productsHorizontalAdapter;
    ProductsHorizontalAdapter newProductsHorizontalAdapter;
    CategoriesHorizontalAdapter categoriesHorizontalAdapter;
    ArrayList<Product> productArrayList = new ArrayList<>();
    ArrayList<Product> newProductArrayList = new ArrayList<>();
    ArrayList<Category> categoryArrayList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.swipeRefreshLayout.setRefreshing(true);


        categoriesHorizontalAdapter = new CategoriesHorizontalAdapter(requireContext(), categoryArrayList);
        productsHorizontalAdapter = new ProductsHorizontalAdapter(requireContext(), productArrayList);
        newProductsHorizontalAdapter = new ProductsHorizontalAdapter(requireContext(), newProductArrayList);

        binding.recyclerViewJustForYou.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewNew.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));


        binding.recyclerViewCategory.setHasFixedSize(true);
        binding.recyclerViewCategory.setHasFixedSize(true);
        binding.recyclerViewNew.setHasFixedSize(true);

        binding.recyclerViewJustForYou.setAdapter(productsHorizontalAdapter);
        binding.recyclerViewNew.setAdapter(newProductsHorizontalAdapter);
        binding.recyclerViewCategory.setAdapter(categoriesHorizontalAdapter);

        getData();
        setListeners();
        return root;
    }

    private void getData() {
        Log.d(Constants.TAG, "getData: Called");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("categories");
        Log.d(Constants.TAG, "getData: " + databaseReference.toString());

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binding.swipeRefreshLayout.setRefreshing(false);
                Log.d(Constants.TAG, "onDataChange: " + snapshot);
                categoryArrayList.clear();
                productArrayList.clear();
                newProductArrayList.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Category category = child.getValue(Category.class);
                    categoryArrayList.add(category);

                }

                int index = new Random().nextInt(categoryArrayList.size());
                int index2 = new Random().nextInt(categoryArrayList.size());

                if (categoryArrayList.get(index).getProducts() != null) {
                    productArrayList.addAll(
                            categoryArrayList.get(index).getProducts().values());
                }


                if (categoryArrayList.get(index2).getProducts() != null) {
                    newProductArrayList.addAll(
                            categoryArrayList.get(index2).getProducts().values());
                }

                productsHorizontalAdapter.notifyDataSetChanged();
                newProductsHorizontalAdapter.notifyDataSetChanged();
                categoriesHorizontalAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.swipeRefreshLayout.setRefreshing(false);
                Log.d(Constants.TAG, "onCancelled: " + error.getDetails());
                Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setListeners() {

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        binding.tvViewAllCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), AllCategoriesActivity.class));
            }
        });

//        cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(HomeScreen.this, CartScreen.class);
//                Toast.makeText(requireContext(), "Cart Screen", Toast.LENGTH_LONG).show();
//                startActivity(intent);
//
//            }
//        });

        productsHorizontalAdapter.setOnProductClickListener(new ProductsHorizontalAdapter.OnProductClickListener() {
            @Override
            public void onProductsClicked(int position, Product product) {
                Intent intent = new Intent(requireContext(), ProductDetailPage.class);
                intent.putExtra(Constants.KEY_PRODUCT, product);
                startActivity(intent);
            }
        });

        newProductsHorizontalAdapter.setOnProductClickListener(new ProductsHorizontalAdapter.OnProductClickListener() {
            @Override
            public void onProductsClicked(int position, Product product) {
                Intent intent = new Intent(requireContext(), ProductDetailPage.class);
                intent.putExtra(Constants.KEY_PRODUCT, product);
                startActivity(intent);
            }
        });

        categoriesHorizontalAdapter.setOnCategoryClickListener((position, category) -> {
            Intent intent = new Intent(requireContext(), AllProductsByCategoryActivity.class);
            intent.putExtra(Constants.KEY_CATEGORY, category);
            startActivity(intent);
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
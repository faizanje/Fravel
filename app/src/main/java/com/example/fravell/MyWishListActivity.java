package com.example.fravell;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fravell.Models.Category;
import com.example.fravell.Models.Product;
import com.example.fravell.Utils.Constants;
import com.example.fravell.Utils.FirebaseUtils;
import com.example.fravell.adapters.ProductsHorizontalAdapter;
import com.example.fravell.databinding.ActivityMyWishListBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class MyWishListActivity extends AppCompatActivity {

    ActivityMyWishListBinding binding;
    ArrayList<Product> productArrayList = new ArrayList<>();
    Map<String, Boolean> favoritesHashMap;
    Category category;
    ProductsHorizontalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyWishListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new ProductsHorizontalAdapter(this, productArrayList);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerView.setAdapter(adapter);

        binding.swipeRefreshLayout.setRefreshing(true);
        getData();
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        adapter.setOnProductClickListener(new ProductsHorizontalAdapter.OnProductClickListener() {
            @Override
            public void onProductsClicked(int position, Product product) {
                Intent intent = new Intent(MyWishListActivity.this, ProductDetailPage.class);
                intent.putExtra(Constants.KEY_PRODUCT, product);
                startActivity(intent);
            }
        });
    }

    private void getData() {
        FirebaseUtils.getFavouritesReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<Map<String, Boolean>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Boolean>>() {};

                Log.d(Constants.TAG, "onDataChange: "  + snapshot.getValue().toString());
                favoritesHashMap = snapshot.getValue(genericTypeIndicator);
                Log.d(Constants.TAG, "favoritesHashMap: " + favoritesHashMap.size());
                FirebaseUtils.getCategoriesReference()
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                binding.swipeRefreshLayout.setRefreshing(false);
                                productArrayList.clear();
                                for (DataSnapshot child : snapshot.getChildren()) {
                                    Category category = child.getValue(Category.class);
                                    for (Product value : category.getProducts().values()) {
                                        if(favoritesHashMap.getOrDefault(value.getId(),false))
                                            productArrayList.add(value);
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
            public void onCancelled(@NonNull DatabaseError error) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
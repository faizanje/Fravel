package com.example.fravell;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.Models.Category;
import com.example.Models.Product;
import com.example.Utils.Constants;
import com.example.adapters.ProductsHorizontalAdapter;
import com.example.fravell.databinding.ActivityAllProductsByCategoryBinding;

import java.util.ArrayList;

public class AllProductsByCategoryActivity extends AppCompatActivity {

    ActivityAllProductsByCategoryBinding binding;
    ArrayList<Product> productArrayList = new ArrayList<>();
    Category category;
    ProductsHorizontalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllProductsByCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        category = (Category) getIntent().getSerializableExtra(Constants.KEY_CATEGORY);
        if (category != null) {
            productArrayList.addAll(category.getProducts().values());
        }
        binding.toolbar2.setTitle(category.getCategoryName());

        adapter = new ProductsHorizontalAdapter(this, productArrayList);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnProductClickListener(new ProductsHorizontalAdapter.OnProductClickListener() {
            @Override
            public void onProductsClicked(int position, Product product) {
                Intent intent = new Intent(AllProductsByCategoryActivity.this, ProductDetailPage.class);
                intent.putExtra(Constants.KEY_PRODUCT, product);
                startActivity(intent);
            }
        });
    }
}
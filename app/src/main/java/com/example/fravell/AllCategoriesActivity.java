package com.example.fravell;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fravell.Models.Category;
import com.example.fravell.Utils.Constants;
import com.example.fravell.adapters.CategoriesVerticalAdapter;
import com.example.fravell.databinding.ActivityAllCategoriesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllCategoriesActivity extends AppCompatActivity {

    ActivityAllCategoriesBinding binding;
    ArrayList<Category> categoryArrayList = new ArrayList<>();
    CategoriesVerticalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new CategoriesVerticalAdapter(this, categoryArrayList);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnCategoryClickListener(new CategoriesVerticalAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClicked(int position, Category category) {
                Intent intent = new Intent(AllCategoriesActivity.this, AllProductsByCategoryActivity.class);
                intent.putExtra(Constants.KEY_CATEGORY, category);
                startActivity(intent);
            }
        });

        getData();


    }

    private void getData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("categories");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(Constants.TAG, "onDataChange: " + snapshot);
                categoryArrayList.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Category category = child.getValue(Category.class);
                    categoryArrayList.add(category);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(Constants.TAG, "onCancelled: " + error.getDetails());
            }
        });
    }
}
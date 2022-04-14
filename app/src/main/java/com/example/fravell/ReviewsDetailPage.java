package com.example.fravell;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fravell.Models.Product;
import com.example.fravell.Models.Review;
import com.example.fravell.Utils.Constants;
import com.example.fravell.Utils.FirebaseUtils;
import com.example.fravell.adapters.ReviewAdapter;
import com.example.fravell.databinding.ActivityReviewsDetailPageBinding;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ReviewsDetailPage extends AppCompatActivity {

    Button writerev;
    ActivityReviewsDetailPageBinding binding;
    ReviewAdapter adapter ;
    ArrayList<Review> reviews = new ArrayList<>();
    Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewsDetailPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        product = (Product) getIntent().getSerializableExtra(Constants.KEY_PRODUCT);

        binding.swipeRefreshLayout.setRefreshing(true);
        adapter = new ReviewAdapter(this,reviews);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        getData();


//        writerev=findViewById(R.id.sendreview);
//        writerev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent=new Intent(ReviewsDetailPage.this,WriteReviewScreen.class);
//                startActivity(intent);

                /*ReviewsFragment revfrag= new ReviewsFragment();
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.Reviewslayout,revfrag);
                transaction.commit();*/
//            }
//        });
    }

    private void getData() {
        FirebaseUtils.getReviewsReference()
                .child(product)

    }
}
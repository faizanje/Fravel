package com.example.fravell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ReviewsDetailPage extends AppCompatActivity {

    Button writerev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_detail_page);

        writerev=findViewById(R.id.sendreview);
        writerev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ReviewsDetailPage.this,WriteReviewScreen.class);
                startActivity(intent);

                /*ReviewsFragment revfrag= new ReviewsFragment();
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.Reviewslayout,revfrag);
                transaction.commit();*/
            }
        });
    }
}
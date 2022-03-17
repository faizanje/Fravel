package com.example.fravell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WriteReviewScreen extends AppCompatActivity {

    Button submitrev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review_screen);

        submitrev=findViewById(R.id.submitreviewbtn);
        submitrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(WriteReviewScreen.this,ProductDetailPage.class);
                Toast.makeText(getApplicationContext(), "Review Submitted", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });
    }
}
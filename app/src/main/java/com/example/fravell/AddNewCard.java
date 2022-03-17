package com.example.fravell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddNewCard extends AppCompatActivity {

    Button addcard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_card);

        addcard=findViewById(R.id.btn_addcard);
        addcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(AddNewCard.this,PaymentPassConfirmScreen.class);
                Toast.makeText(getApplicationContext(), "Enter Password to Confirm", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });

    }
}
package com.example.fravell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentMethods extends AppCompatActivity {


    Button card_details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_methods);

        card_details=findViewById(R.id.btn_newcarddetails);
        card_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(PaymentMethods.this,AddNewCard.class);
                Toast.makeText(getApplicationContext(), "Add Details", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });
    }
}
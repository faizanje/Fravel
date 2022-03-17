package com.example.fravell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentPassConfirmScreen extends AppCompatActivity {

    Button card_change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_pass_confirm_screen);

        card_change=findViewById(R.id.btn_verification_pay_card);
        card_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(PaymentPassConfirmScreen.this,AddShippingAddress.class);
                Toast.makeText(getApplicationContext(), "Password Success", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });

    }
}
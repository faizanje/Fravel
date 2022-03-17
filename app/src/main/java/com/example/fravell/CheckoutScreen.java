package com.example.fravell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CheckoutScreen extends AppCompatActivity {

    Button pay;
    TextView btn1,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_screen);

        pay=findViewById(R.id.btn_pay_amount);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(CheckoutScreen.this,AddShippingAddress.class);
                Toast.makeText(getApplicationContext(), "Add shipping Address", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });

        btn1=findViewById(R.id.btn_add_newcard);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(CheckoutScreen.this,AddShippingAddress.class);
                Toast.makeText(getApplicationContext(), "Add new Address", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });
        btn2=findViewById(R.id.btn_add_newcard1);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(CheckoutScreen.this,PaymentMethods.class);
                Toast.makeText(getApplicationContext(), "Add new Card", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });
    }
}
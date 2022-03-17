package com.example.fravell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CustomOrderDetails extends AppCompatActivity {

    Button custom;
    LinearLayout home,messages,cart,account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_order_details);

        custom=findViewById(R.id.btn_custom1);
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(CustomOrderDetails.this,MessagesMainScreen.class);
                Toast.makeText(getApplicationContext(), "Custom details added", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });
    }
}
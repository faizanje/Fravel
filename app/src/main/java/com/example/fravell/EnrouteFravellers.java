package com.example.fravell;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class EnrouteFravellers extends AppCompatActivity {

    RelativeLayout message;
    LinearLayout home,messages,cart,account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroute_fravellers);

        /*message=findViewById(R.id.btn_message);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(EnrouteFravellers.this,MessagesMainScreen.class);
                Toast.makeText(getApplicationContext(), "Chats", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(EnrouteFravellers.this,CartScreen.class);
                Toast.makeText(getApplicationContext(), "Cart Screen", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(EnrouteFravellers.this,HomeScreen.class);
                Toast.makeText(getApplicationContext(), "Product Detail Page", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });
        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(EnrouteFravellers.this,CustomOrderDetails.class);
                Toast.makeText(getApplicationContext(), "Custom Order", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });*/

    }
}
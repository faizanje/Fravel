package com.example.fravell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Models.CartItem;
import com.example.Models.UserAddress;
import com.example.Utils.Constants;
import com.example.Utils.FirebaseUtils;
import com.example.fravell.databinding.ActivityCheckoutScreenBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CheckoutScreen extends AppCompatActivity {

    Button pay;
    TextView btn1, btn2;
    int totalAmount = 0;
    ArrayList<CartItem> cartItemArrayList = new ArrayList<>();
    ActivityCheckoutScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cartItemArrayList = (ArrayList<CartItem>) getIntent().getSerializableExtra(Constants.KEY_CART_LIST);
        totalAmount = getIntent().getIntExtra(Constants.KEY_TOTAL_AMOUNT, 0);

        binding.tvTotalCost.setText("$" + totalAmount);


        getData();
        binding.btnAddDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutScreen.this, AddShippingAddress.class);
                Toast.makeText(getApplicationContext(), "Add shipping Address", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

        binding.btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutScreen.this, PaymentMethods.class);
                Toast.makeText(getApplicationContext(), "Add new Card", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

        pay = findViewById(R.id.btn_pay_amount);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CheckoutScreen.this, AddShippingAddress.class);
                Toast.makeText(getApplicationContext(), "Add shipping Address", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });

        btn1 = findViewById(R.id.btn_add_newcard);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CheckoutScreen.this, AddShippingAddress.class);
                Toast.makeText(getApplicationContext(), "Add new Address", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });
        btn2 = findViewById(R.id.btn_add_newcard1);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CheckoutScreen.this, PaymentMethods.class);
                Toast.makeText(getApplicationContext(), "Add new Card", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });
    }

    private void getData() {
        FirebaseUtils.getCurrentUserAddressReference()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            UserAddress userAddress = snapshot.getValue(UserAddress.class);
                            if (userAddress != null) {
                                binding.layoutNoAddress.setVisibility(View.GONE);
                                extracted(userAddress);
                                binding.layoutAddressDetails.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void extracted(UserAddress userAddress) {
        binding.tvUsername.setText(userAddress.getName());
        binding.tvAddress.setText(userAddress.getAddress());
        binding.tvCityCountry.setText(String.format("%s, %s %s", userAddress.getCity(), userAddress.getProvince(), userAddress.getCountry()));
    }
}
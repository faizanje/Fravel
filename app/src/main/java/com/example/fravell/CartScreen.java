package com.example.fravell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.Models.CartItem;
import com.example.Utils.CartUtils;
import com.example.Utils.Constants;
import com.example.Utils.FirebaseUtils;
import com.example.adapters.CartAdapter;
import com.example.fravell.databinding.ActivityCartScreenBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartScreen extends AppCompatActivity {

    Button checkout;
    LinearLayout home, messages, cart, account;
    CartAdapter adapter;
    ArrayList<CartItem> cartItemArrayList = new ArrayList<>();
    ActivityCartScreenBinding binding;
    int deliveryPrice = Constants.DELIVERY_PRICE;
    int orderAmount = 0;
    int totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        adapter = new CartAdapter(this, cartItemArrayList);
        binding.recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewCart.setAdapter(adapter);


        getData();

        adapter.setOnCartItemClickListener(new CartAdapter.OnCartItemClickListener() {
            @Override
            public void onCartItemClicked(int position, CartItem cartItem) {
                Intent intent = new Intent(CartScreen.this, ProductDetailPage.class);
                intent.putExtra(Constants.KEY_PRODUCT, cartItem.getProduct());
                startActivity(intent);
            }

            @Override
            public void onRemoveCartItemClicked(int position, CartItem cartItem) {
                CartUtils.removeItem(cartItem);
            }

            @Override
            public void onIncrementQuantityClicked(int position, CartItem cartItem) {
                CartUtils.increment(cartItem);
            }

            @Override
            public void onDecrementQuantityClicked(int position, CartItem cartItem) {
                CartUtils.decrement(cartItem);
            }
        });
        checkout = findViewById(R.id.btn_checkout_cart);
        home = findViewById(R.id.home_screen_btn);
        messages = findViewById(R.id.messages_screen_btn);
        cart = findViewById(R.id.cart_screen_btn);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cartItemArrayList.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Cart is empty", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(CartScreen.this, CheckoutScreen.class);
                intent.putExtra(Constants.KEY_CART_LIST, cartItemArrayList);
                intent.putExtra(Constants.KEY_TOTAL_AMOUNT, totalAmount);
                Toast.makeText(getApplicationContext(), "Select Payment Method", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CartScreen.this, CartScreen.class);
                Toast.makeText(getApplicationContext(), "Cart Screen", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CartScreen.this, HomeScreen.class);
                Toast.makeText(getApplicationContext(), "Product Detail Page", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });
        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CartScreen.this, CustomOrderDetails.class);
                Toast.makeText(getApplicationContext(), "Custom Order", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });

    }

    private void getData() {
        FirebaseUtils.getCartReference()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        cartItemArrayList.clear();
                        deliveryPrice = 0;
                        orderAmount = 0;
                        totalAmount = 0;

                        for (DataSnapshot child : snapshot.getChildren()) {
                            CartItem cartItem = child.getValue(CartItem.class);
                            cartItemArrayList.add(cartItem);
                            orderAmount += (cartItem.getQuantity() * cartItem.getProduct().getPrice());
                        }

                        if (cartItemArrayList.isEmpty()) {
                            deliveryPrice = 0;
                            orderAmount = 0;
                            totalAmount = 0;
                        } else {
                            deliveryPrice = Constants.DELIVERY_PRICE;
                            totalAmount = deliveryPrice + orderAmount;
                        }

                        binding.tvDeliveryAmount.setText("$" + deliveryPrice);
                        binding.tvOrderAmount.setText("$" + orderAmount);
                        binding.tvTotalAmount.setText("$" + totalAmount);

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
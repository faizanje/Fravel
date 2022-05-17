package com.example.fravell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fravell.Models.CartItem;
import com.example.fravell.Models.Order;
import com.example.fravell.Models.OrderStatus;
import com.example.fravell.Utils.Constants;
import com.example.fravell.Utils.DateTimeUtils;
import com.example.fravell.Utils.DialogUtil;
import com.example.fravell.Utils.FirebaseUtils;
import com.example.fravell.Utils.NumberUtils;
import com.example.fravell.adapters.OrderedProductAdapter;
import com.example.fravell.databinding.ActivityOrderDetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class OrderDetailActivity extends AppCompatActivity {

    ActivityOrderDetailBinding binding;
    Order order;
    OrderedProductAdapter adapter;
    ArrayList<CartItem> cartItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        order = (Order) getIntent().getSerializableExtra(Constants.KEY_ORDER);

        cartItems.addAll(order.getCartItemArrayList());
        adapter = new OrderedProductAdapter(this, cartItems, order.getOrderStatus().equals(OrderStatus.DELIVERED.getStatus()));
        binding.recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView2.setAdapter(adapter);

        setListeners();
        populateViews();
    }

    private void setListeners() {
        adapter.setOnCartItemClickListener(new OrderedProductAdapter.OnCartItemClickListener() {
            @Override
            public void onCartItemClicked(int position, CartItem cartItem) {
                Intent intent = new Intent(OrderDetailActivity.this, WriteReviewScreen.class);
                intent.putExtra(Constants.KEY_CART_ITEM, cartItem);
                intent.putExtra(Constants.KEY_CART_ITEM_INDEX, order.getCartItemArrayList().indexOf(cartItem));
                intent.putExtra(Constants.KEY_ORDER_ID, order.getOrderId());
                startActivity(intent);
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.showSimpleProgressDialog(OrderDetailActivity.this);
                order.setOrderStatus(OrderStatus.DELIVERED.getStatus());
                FirebaseUtils.getOrdersReference()
                        .child(order.getOrderId())
                        .setValue(order)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                DialogUtil.closeProgressDialog();
                                Toast.makeText(OrderDetailActivity.this, "Order marked as completed", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
            }
        });
    }

    private void populateViews() {
        binding.tvDate.setText("Order # " + order.getOrderId());
        binding.tvStatus.setText(order.getOrderStatus());
        binding.tvDate.setText(DateTimeUtils.millisecondsToDate(order.getTimeInMillis(), Constants.DATE_FORMAT));
        int quantity = 0;
        double totalAmount = 7;
        for (CartItem cartItem : cartItems) {
            quantity += cartItem.getQuantity();
            totalAmount += NumberUtils.getDiscountedPrice(cartItem.getProduct().getPrice()) * cartItem.getQuantity();
        }

        binding.tvQuantity.setText(order.getCartItemArrayList().size() + " items");
        binding.tvTotalAmount.setText(NumberUtils.round(totalAmount, 1) + "$");

        String shippingAddress = String.format("%s, %s, %s", order.getUserAddress().getAddress(), order.getUserAddress().getCity(), order.getUserAddress().getCountry());
        binding.tvShippingAddress.setText(shippingAddress);




        boolean showButton = order.getOrderStatus().equals(OrderStatus.PROCESSING.getStatus());
        binding.button.setVisibility(showButton ? View.VISIBLE : View.GONE);


//        if (order.getOrderStatus().equals(OrderStatus.DELIVERED.getStatus())) {
//            if (order.hasFeedbackLeft()) {
//                binding.button.setVisibility(View.GONE);
//            } else {
//                binding.button.setText("Leave feedback");
//                binding.button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(OrderDetailActivity.this, WriteReviewScreen.class);
//                        intent.putExtra(Constants.KEY_ORDER, order);
//                        startActivity(intent);
//                    }
//                });
//            }
//
//        } else if (order.getOrderStatus().equals(OrderStatus.PROCESSING.getStatus())) {
//            binding.button.setText("Mark as delivered");
//            binding.button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    DialogUtil.showSimpleProgressDialog(OrderDetailActivity.this);
//                    order.setOrderStatus(OrderStatus.DELIVERED.getStatus());
//                    FirebaseUtils.getOrdersReference()
//                            .child(order.getOrderId())
//                            .setValue(order)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    DialogUtil.closeProgressDialog();
//                                    Toast.makeText(OrderDetailActivity.this, "Order marked as completed", Toast.LENGTH_SHORT).show();
//                                    finish();
//                                }
//                            });
//                }
//            });
//        }
    }
}
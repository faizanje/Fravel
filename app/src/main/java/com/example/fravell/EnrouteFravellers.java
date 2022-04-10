package com.example.fravell;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.Models.AvailableFraveller;
import com.example.Models.CustomOrder;
import com.example.Utils.Constants;
import com.example.Utils.DateTimeUtils;
import com.example.Utils.FirebaseUtils;
import com.example.Utils.RVEmptyObserver;
import com.example.adapters.AvailableFravellerAdapter;
import com.example.fravell.databinding.ActivityEnrouteFravellersBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EnrouteFravellers extends AppCompatActivity {

    RelativeLayout message;
    LinearLayout home, messages, cart, account;
    ActivityEnrouteFravellersBinding binding;
    AvailableFravellerAdapter adapter;
    ArrayList<AvailableFraveller> availableFravellerArrayList = new ArrayList<>();
    CustomOrder customOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEnrouteFravellersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        customOrder = (CustomOrder) getIntent().getSerializableExtra(Constants.KEY_CUSTOM_ORDER);
        adapter = new AvailableFravellerAdapter(this, availableFravellerArrayList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.registerAdapterDataObserver(new RVEmptyObserver(binding.recyclerView, binding.layoutEmpty));

        getData();
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

    private void getData() {

        FirebaseUtils.getAvailableFravellersReference()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        availableFravellerArrayList.clear();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            Log.d(Constants.TAG, "onDataChange: " + child);
                            AvailableFraveller availableFraveller = child.getValue(AvailableFraveller.class);
                            boolean isInBetweenDates = DateTimeUtils.isInBetweenDates(
                                    new Pair<>(availableFraveller.getFromDateInMillis(), availableFraveller.getToDateInMillis()),
                                    new Pair<>(customOrder.getStartDate(), customOrder.getEndDate())
                            );

                            boolean canDeliver = availableFraveller.getFromCity().equals(customOrder.getFromCity()) &&
                                    availableFraveller.getToCity().equals(customOrder.getToCity());
                            if (isInBetweenDates && canDeliver) {
                                availableFravellerArrayList.add(availableFraveller);
                            }
//                            if (availableFraveller.getFromDateInMillis() >= customOrder.getStartDate() &&
//                                    availableFraveller.getToDateInMillis() <= customOrder.getEndDate()) {
//                            availableFravellerArrayList.add(availableFraveller);
//                            }
                        }
                        binding.layoutSearching.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
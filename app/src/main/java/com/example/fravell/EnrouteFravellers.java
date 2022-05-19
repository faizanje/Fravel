package com.example.fravell;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fravell.Models.AvailableFraveller;
import com.example.fravell.Models.CustomOrder;
import com.example.fravell.Models.FravellerCriteria;
import com.example.fravell.Models.OrderStatus;
import com.example.fravell.Utils.Constants;
import com.example.fravell.Utils.DateTimeUtils;
import com.example.fravell.Utils.DialogUtil;
import com.example.fravell.Utils.FirebaseUtils;
import com.example.fravell.Utils.RVEmptyObserver;
import com.example.fravell.adapters.AvailableFravellerAdapter;
import com.example.fravell.databinding.ActivityEnrouteFravellersBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EnrouteFravellers extends AppCompatActivity {

    RelativeLayout message;
    LinearLayout home, messages, cart, account;
    ActivityEnrouteFravellersBinding binding;
    AvailableFravellerAdapter adapter;
    ArrayList<AvailableFraveller> availableFravellerArrayList = new ArrayList<>();
    FravellerCriteria fravellerCriteria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEnrouteFravellersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        fravellerCriteria = (FravellerCriteria) getIntent().getSerializableExtra(Constants.KEY_CUSTOM_ORDER);
        adapter = new AvailableFravellerAdapter(this, availableFravellerArrayList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.registerAdapterDataObserver(new RVEmptyObserver(binding.recyclerView, binding.layoutEmpty));
        adapter.setOnAvailableFravellerClickListener(new AvailableFravellerAdapter.OnAvailableFravellerClickListener() {
            @Override
            public void onAvailableFravellerClicked(int position, AvailableFraveller availableFraveller) {
                DialogUtil.showSimpleProgressDialog(EnrouteFravellers.this);
                DatabaseReference reference = FirebaseUtils.getCustomOrdersReference().push();

                CustomOrder customOrder = new CustomOrder(
                        reference.getKey(),
                        100,
                        System.currentTimeMillis(),
                        OrderStatus.PROCESSING.getStatus(),
                        fravellerCriteria,
                        availableFraveller
                );
                reference.setValue(customOrder)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(EnrouteFravellers.this, "Custom order created", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EnrouteFravellers.this, MyCustomOrderActivity.class));
                                finish();
                            }
                        });
            }
        });
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
                                    new Pair<>(fravellerCriteria.getStartDate(), fravellerCriteria.getEndDate())
                            );

                            boolean canDeliver = availableFraveller.getFromCity().equals(fravellerCriteria.getFromCity()) &&
                                    availableFraveller.getToCity().equals(fravellerCriteria.getToCity());
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
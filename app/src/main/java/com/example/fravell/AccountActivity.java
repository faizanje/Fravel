package com.example.fravell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fravell.Models.AccountItem;
import com.example.fravell.adapters.AccountListAdapter;
import com.example.fravell.databinding.ActivityAccountBinding;
import com.google.firebase.auth.FirebaseAuth;

public class AccountActivity extends AppCompatActivity {

    AccountListAdapter adapter;
    AccountItem[] accountItems;
    ActivityAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        accountItems = new AccountItem[]{
                new AccountItem("My Orders", R.drawable.profile),
                new AccountItem("My Wishlist", R.drawable.heart),
                new AccountItem("Settings", R.drawable.settings),
                new AccountItem("Help", R.drawable.chat),
                new AccountItem("Privacy policy", R.drawable.paper)
        };

        adapter = new AccountListAdapter(this, accountItems);
        binding.listView.setAdapter(adapter);
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AccountActivity.this, MainActivity.class));
                finish();
            }
        });
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        startActivity(new Intent(AccountActivity.this, MyOrdersActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(AccountActivity.this, MyWishListActivity.class));
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;


                }
            }
        });
    }
}
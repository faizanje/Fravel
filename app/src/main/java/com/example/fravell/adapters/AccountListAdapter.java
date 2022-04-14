package com.example.fravell.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.fravell.Models.AccountItem;
import com.example.fravell.R;
import com.example.fravell.databinding.ItemProfileListBinding;

public class AccountListAdapter extends ArrayAdapter<AccountItem> {

    private final Activity context;
    private final AccountItem[] accountItems;

    public AccountListAdapter(Activity context, AccountItem[] accountItems) {
        super(context, R.layout.item_profile_list, accountItems);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.accountItems = accountItems;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        ItemProfileListBinding binding = ItemProfileListBinding.inflate(inflater);
        AccountItem accountItem = accountItems[position];
        binding.imageView.setImageResource(accountItem.getImageId());
        binding.tvTitle.setText(accountItem.getTitle());

        return binding.getRoot();

    }

}
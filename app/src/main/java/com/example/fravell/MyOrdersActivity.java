package com.example.fravell;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.fravell.adapters.SectionsPagerAdapter;
import com.example.fravell.databinding.ActivityMyOrdersBinding;
import com.example.fravell.ui.CompletedOrdersFragment;
import com.example.fravell.ui.ProcessingOrdersFragment;
import com.google.android.material.tabs.TabLayout;

public class MyOrdersActivity extends AppCompatActivity {

    private ActivityMyOrdersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String[] titles = new String[]{"Delivered", "Processing"};
        Fragment[] fragments = new Fragment[]{new CompletedOrdersFragment(), new ProcessingOrdersFragment()};
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(
                getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                titles,
                fragments,
                this);
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);


    }
}
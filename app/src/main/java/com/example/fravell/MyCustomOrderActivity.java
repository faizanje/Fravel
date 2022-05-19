package com.example.fravell;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.fravell.adapters.SectionsPagerAdapter;
import com.example.fravell.databinding.ActivityMyCustomOrderBinding;
import com.example.fravell.ui.CustomCompletedFragment;
import com.example.fravell.ui.CustomInProgressFragment;
import com.google.android.material.tabs.TabLayout;

public class MyCustomOrderActivity extends AppCompatActivity {

    private ActivityMyCustomOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyCustomOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String[] titles = new String[]{"Delivered", "In-Progress"};
        Fragment[] fragments = new Fragment[]{new CustomCompletedFragment(), new CustomInProgressFragment()};
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
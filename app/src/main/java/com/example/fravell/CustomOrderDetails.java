package com.example.fravell;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.example.fravell.Models.FravellerCriteria;
import com.example.fravell.Utils.Constants;
import com.example.fravell.Utils.DateTimeUtils;
import com.example.fravell.databinding.ActivityCustomOrderDetailsBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CustomOrderDetails extends AppCompatActivity {

    Button custom;
    LinearLayout home, messages, cart, account;
    ArrayAdapter<String> fromCityAdapter, toCityAdapter;
    ActivityCustomOrderDetailsBinding binding;
    List<String> fromCities, toCities;
    Pair<Long, Long> selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCustomOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fromCities = Arrays.asList(getResources().getStringArray(R.array.cities));
        toCities = Arrays.asList(getResources().getStringArray(R.array.cities));
        fromCityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fromCities);
        toCityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, toCities);

        binding.autoCompleteFromCity.setAdapter(fromCityAdapter);
        binding.autoCompleteToCity.setAdapter(toCityAdapter);

        binding.etDateRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker<Pair<Long, Long>> picker = MaterialDatePicker.Builder.dateRangePicker()
                        .build();
                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long, Long> selection) {
                        CustomOrderDetails.this.selection = selection;
                        Log.d(Constants.TAG, "onPositiveButtonClick:" + selection.first + ", " + selection.second);
                        String firstDate = DateTimeUtils.millisecondsToDate(selection.first);
                        String secondDate = DateTimeUtils.millisecondsToDate(selection.second);
                        binding.etDateRange.setText(String.format("%s - %s", firstDate, secondDate));
                        Toast.makeText(CustomOrderDetails.this, "", Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show(getSupportFragmentManager(), this.toString());
            }
        });

        binding.autoCompleteFromCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!fromCities.contains(binding.autoCompleteFromCity.getText().toString())) {
                        binding.tilFromCity.setError("Invalid city.");
                        binding.tilFromCity.setErrorEnabled(true);
                    } else {
                        binding.tilFromCity.setError("");
                        binding.tilFromCity.setErrorEnabled(false);
                    }
                }
            }
        });

        binding.autoCompleteToCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!toCities.contains(binding.autoCompleteToCity.getText().toString())) {
                        binding.tilToCity.setError("Invalid city.");
                        binding.tilToCity.setErrorEnabled(true);
                    } else {
                        binding.tilToCity.setError("");
                        binding.tilToCity.setErrorEnabled(false);
                    }
                }
            }
        });
        custom = findViewById(R.id.btn_custom1);
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fromCity = binding.autoCompleteFromCity.getText().toString();
                String toCity = binding.autoCompleteToCity.getText().toString();
                String description = binding.etDescription.getText().toString();
                String additionalNotes = binding.etAdditionalNotes.getText().toString();
                String uid = FirebaseAuth.getInstance().getUid();
                long fromDate = selection.first;
                long toDate = selection.second;
                FravellerCriteria fravellerCriteria = new FravellerCriteria(UUID.randomUUID().toString(), toCity, fromCity, fromDate, toDate, description, additionalNotes, uid);
                Intent intent = new Intent(CustomOrderDetails.this, EnrouteFravellers.class);
                intent.putExtra(Constants.KEY_CUSTOM_ORDER, fravellerCriteria);
                startActivity(intent);

            }
        });
    }
}
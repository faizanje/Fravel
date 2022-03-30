package com.example.fravell;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Models.UserAddress;
import com.example.Utils.FirebaseUtils;
import com.example.fravell.databinding.ActivityAddShippingAddressBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class AddShippingAddress extends AppCompatActivity {

    Button addshipping;
    ActivityAddShippingAddressBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddShippingAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//        binding.btnConfirmAddress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(AddShippingAddress.this, "Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });

        binding.btnConfirmAddress.setOnClickListener(view -> {

            String name = binding.etName.getText().toString().trim();
            String address = binding.etAddress.getText().toString().trim();
            String city = binding.etCity.getText().toString().trim();
            String zipCode = binding.etZipCode.getText().toString().trim();
            String province = binding.etProvince.getText().toString().trim();
            String country = binding.etCountry.getText().toString().trim();

            if (name.isEmpty() || address.isEmpty() || city.isEmpty() || zipCode.isEmpty() || province.isEmpty() || country.isEmpty()) {
                Toast.makeText(AddShippingAddress.this, "Empty fields are not allowed", Toast.LENGTH_SHORT).show();
                return;
            }

            UserAddress userAddress = new UserAddress(name, address, city, zipCode, province, country);

            FirebaseUtils.getCurrentUserAddressReference()
                    .setValue(userAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            finish();
                            Toast.makeText(AddShippingAddress.this, "Shipping details added", Toast.LENGTH_SHORT).show();
                        }
                    });

//                Intent intent = new Intent(AddShippingAddress.this, OrderSuccessScreen.class);
//                Toast.makeText(getApplicationContext(), "Order Placed Successfully", Toast.LENGTH_LONG).show();
//                startActivity(intent);

        });

    }
}
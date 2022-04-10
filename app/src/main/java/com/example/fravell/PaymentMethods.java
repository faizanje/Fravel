package com.example.fravell;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Models.MyCreditCard;
import com.example.Utils.Constants;
import com.example.Utils.FirebaseUtils;
import com.example.fravell.databinding.ActivityPaymentMethodsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.stripe.android.model.CardParams;
import com.stripe.android.view.CardValidCallback;

import java.util.Set;

public class PaymentMethods extends AppCompatActivity {


    Button card_details;
    ActivityPaymentMethodsBinding binding;
    boolean isValid = false;
    MyCreditCard myCreditCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPaymentMethodsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myCreditCard = (MyCreditCard) getIntent().getSerializableExtra(Constants.KEY_MY_CREDIT_CARD);
        if(myCreditCard != null){
            populateCreditCard();
        }
        binding.cardMultilineWidget.setShouldShowPostalCode(false);
        binding.cardMultilineWidget.setCardValidCallback(new CardValidCallback() {
            @Override
            public void onInputChanged(boolean b, @NonNull Set<? extends Fields> set) {
                Log.d(Constants.TAG, "onInputChanged: " + b);
                isValid = b;
                for (Fields fields : set) {
                    Log.d(Constants.TAG, "onInputChanged: " + fields);
                    Log.d(Constants.TAG, "onInputChanged: " + fields.name());
                    Log.d(Constants.TAG, "onInputChanged: " + fields.ordinal());
                }
            }
        });

        card_details = findViewById(R.id.btn_newcarddetails);
        card_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isValid) {
                    Toast.makeText(PaymentMethods.this, "Card information is invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                CardParams cardParams = binding.cardMultilineWidget.getCardParams();
                String cardNumber = cardParams.getNumber$payments_core_release();
                int expiryMonth = cardParams.getExpMonth$payments_core_release();
                int expiryYear = cardParams.getExpYear$payments_core_release();
                String cvc = cardParams.getCvc$payments_core_release();
                String cardBrand = cardParams.getBrand().toString();

                MyCreditCard myCreditCard = new MyCreditCard(cardNumber, expiryMonth, expiryYear, cvc, cardBrand);
                FirebaseUtils.getCardsReference().setValue(myCreditCard)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                finish();
                                Toast.makeText(PaymentMethods.this, "Credit card detail added", Toast.LENGTH_SHORT).show();
                            }
                        });
//                Intent intent=new Intent(PaymentMethods.this,AddNewCard.class);
//                Toast.makeText(getApplicationContext(), "Add Details", Toast.LENGTH_LONG).show();
//                startActivity(intent);

            }
        });
    }

    private void populateCreditCard() {
        binding.cardMultilineWidget.setCardNumber(myCreditCard.getCardNumber());
        binding.cardMultilineWidget.setExpiryDate(myCreditCard.getExpiryMonth(),myCreditCard.getExpiryYear());
        binding.cardMultilineWidget.setCvcCode(myCreditCard.getCvc());
        binding.btnNewcarddetails.setText("Update card");
    }
}
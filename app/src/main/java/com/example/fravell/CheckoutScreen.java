package com.example.fravell;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.fravell.Models.CartItem;
import com.example.fravell.Models.MyCreditCard;
import com.example.fravell.Models.Order;
import com.example.fravell.Models.OrderStatus;
import com.example.fravell.Models.UserAddress;
import com.example.fravell.Utils.Constants;
import com.example.fravell.Utils.DialogUtil;
import com.example.fravell.Utils.FirebaseUtils;
import com.example.fravell.Utils.NumberUtils;
import com.example.fravell.databinding.ActivityCheckoutScreenBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.model.CardBrand;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.payments.paymentlauncher.PaymentLauncher;
import com.stripe.android.payments.paymentlauncher.PaymentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class CheckoutScreen extends AppCompatActivity {

    Button pay;
    TextView btn1, btn2;
    double totalAmount = 0;
    ArrayList<CartItem> cartItemArrayList = new ArrayList<>();
    ActivityCheckoutScreenBinding binding;
    String paymentIntentClientSecret;
    MyCreditCard myCreditCard;
    UserAddress userAddress;
    private PaymentLauncher paymentLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        cartItemArrayList = (ArrayList<CartItem>) getIntent().getSerializableExtra(Constants.KEY_CART_LIST);
        totalAmount = getIntent().getDoubleExtra(Constants.KEY_TOTAL_AMOUNT, 0);

        binding.tvTotalCost.setText("$" + NumberUtils.round(totalAmount, 1));


        init();
        getData();
        binding.btnAddDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutScreen.this, AddShippingAddress.class);
                Toast.makeText(getApplicationContext(), "Add shipping Address", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

        binding.btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutScreen.this, PaymentMethods.class);
                Toast.makeText(getApplicationContext(), "Add new Card", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

        pay = findViewById(R.id.btn_pay_amount);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userAddress == null) {
                    Toast.makeText(CheckoutScreen.this, "User address is null", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (myCreditCard == null) {
                    Toast.makeText(CheckoutScreen.this, "Payment method is null", Toast.LENGTH_SHORT).show();
                    return;
                }

                payAmount();
//                Intent intent = new Intent(CheckoutScreen.this, OrderSuccessScreen.class);
//                startActivity(intent);

            }

        });

        btn1 = findViewById(R.id.btn_add_newcard);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CheckoutScreen.this, AddShippingAddress.class);
                Toast.makeText(getApplicationContext(), "Add new Address", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });
        btn2 = findViewById(R.id.btn_add_newcard1);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CheckoutScreen.this, PaymentMethods.class);
                intent.putExtra(Constants.KEY_MY_CREDIT_CARD, myCreditCard);
                Toast.makeText(getApplicationContext(), "Add new Card", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });
    }

    private void init() {
        final PaymentConfiguration paymentConfiguration
                = PaymentConfiguration.getInstance(getApplicationContext());
        paymentLauncher = PaymentLauncher.Companion.create(
                this,
                paymentConfiguration.getPublishableKey(),
                paymentConfiguration.getStripeAccountId(),
                this::onPaymentResult
        );
    }

    /**
     * Get client secret from server
     * Charge stripe by providing card details to stripe
     * Observe the callback function that stripe provides
     * 1. If failed, then throw the error and show toast message
     * 2. If succes, then save user details in user reference in database
     * */
    private void onPaymentResult(PaymentResult paymentResult) {
        String message = "";
        boolean success = false;
        if (paymentResult instanceof PaymentResult.Completed) {
            message = "Completed!";
            success = true;
        } else if (paymentResult instanceof PaymentResult.Canceled) {
            message = "Canceled!";
        } else if (paymentResult instanceof PaymentResult.Failed) {
            // This string comes from the PaymentIntent's error message.
            // See here: https://stripe.com/docs/api/payment_intents/object#payment_intent_object-last_payment_error-message
            message = "Failed: "
                    + ((PaymentResult.Failed) paymentResult).getThrowable().getMessage();
        }

        if (!success) {
            DialogUtil.closeProgressDialog();
            Toast.makeText(this, "Error: " + message, Toast.LENGTH_LONG).show();
        } else {
            saveOrderInDatabase();
        }
    }

    private void saveOrderInDatabase() {
        int oneTimeID = (int) (SystemClock.uptimeMillis() % 99999999);
        double totalAmount = NumberUtils.getTotalAmountAndQuantity(cartItemArrayList);
        Order order = new Order(String.valueOf(oneTimeID), totalAmount, System.currentTimeMillis(), OrderStatus.PROCESSING.getStatus(), cartItemArrayList, userAddress);
        DialogUtil.showSimpleProgressDialog(this);
        FirebaseUtils.getOrdersReference()
                .child(order.getOrderId())
                .setValue(order)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        DialogUtil.closeProgressDialog();
                        startActivity(new Intent(CheckoutScreen.this, OrderSuccessScreen.class));
                    }
                });
    }

    private void payAmount() {

        DialogUtil.showSimpleProgressDialog(this);


        String receiptEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        //Performing HTTP request to fetch client_secret from backend server
        AndroidNetworking.post(Constants.BASE_URL + "/create-payment-intent")
                .addBodyParameter("price", String.valueOf(totalAmount))
                .addBodyParameter("email", receiptEmail)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(Constants.TAG, "onResponse: " + response.toString());
                        try {
                            paymentIntentClientSecret = response.getString("client_secret");
                            stripeCharge();
                        } catch (JSONException e) {
                            DialogUtil.closeProgressDialog();
                            Toast.makeText(CheckoutScreen.this, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        DialogUtil.closeProgressDialog();

                        //Toast.makeText(Payment_Main.this, "Something went wrong with the server. Please try again later.", Toast.LENGTH_SHORT).show();
                        Log.d(Constants.TAG, "onError: " + error.getErrorDetail());
                        Log.d(Constants.TAG, "onError: " + error.getErrorBody());
                        Log.d(Constants.TAG, "onError: " + error.getErrorCode());
                        try {
                            Log.d(Constants.TAG, "onError: " + error.getResponse().body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void stripeCharge() {

        PaymentMethodCreateParams.Card card = new PaymentMethodCreateParams.Card.Builder()
                .setNumber(myCreditCard.getCardNumber())
                .setExpiryMonth(myCreditCard.getExpiryMonth())
                .setExpiryYear(myCreditCard.getExpiryYear())
                .setCvc(myCreditCard.getCvc())
                .build();
        PaymentMethodCreateParams paymentMethodCreateParams = PaymentMethodCreateParams.create(card);
        final ConfirmPaymentIntentParams confirmParams =
                ConfirmPaymentIntentParams.createWithPaymentMethodCreateParams(paymentMethodCreateParams, paymentIntentClientSecret);
        paymentLauncher.confirm(confirmParams);
    }

    private void getData() {
        FirebaseUtils.getCurrentUserAddressReference()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            userAddress = snapshot.getValue(UserAddress.class);
                            if (userAddress != null) {
                                binding.layoutNoAddress.setVisibility(View.GONE);
                                populateUserAddress(userAddress);
                                binding.layoutAddressDetails.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        FirebaseUtils.getCardsReference()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            MyCreditCard myCreditCard = snapshot.getValue(MyCreditCard.class);
                            if (myCreditCard != null) {
                                CheckoutScreen.this.myCreditCard = myCreditCard;
                                binding.layoutNoCard.setVisibility(View.GONE);
                                populateUserCard(myCreditCard);
                                binding.layoutCardDetails.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void populateUserAddress(UserAddress userAddress) {
        binding.tvUsername.setText(userAddress.getName());
        binding.tvAddress.setText(userAddress.getAddress());
        binding.tvCityCountry.setText(String.format("%s, %s %s", userAddress.getCity(), userAddress.getProvince(), userAddress.getCountry()));
    }

    private void populateUserCard(MyCreditCard myCreditCard) {
        CardBrand cardBrand = CardBrand.valueOf(myCreditCard.getCardBrand());
        binding.cardBrandView.setBrand(cardBrand);
        String cardNumber = myCreditCard.getCardNumber();
        String obscuredCardNumber = "**** **** **** " + cardNumber.substring(12, 16);
        binding.tvCardNumber.setText(obscuredCardNumber);
    }
}
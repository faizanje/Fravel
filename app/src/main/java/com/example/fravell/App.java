package com.example.fravell;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.example.Utils.Constants;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.stripe.android.PaymentConfiguration;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG);
        PaymentConfiguration.init(
                getApplicationContext(),
                Constants.STRIPE_PUBLISHABLE_KEY
        );
        AndroidNetworking.initialize(getApplicationContext());

    }
}

package com.example.fravell;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.example.fravell.Utils.Constants;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.stripe.android.PaymentConfiguration;

import io.paperdb.Paper;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        PaymentConfiguration.init(
                getApplicationContext(),
                Constants.STRIPE_PUBLISHABLE_KEY
        );
        AndroidNetworking.initialize(getApplicationContext());
        Paper.init(getApplicationContext());
    }
}

package com.example.Utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {
    public static DatabaseReference getCurrentUserAddressReference(){
        return FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getUid())
                .child("address");
    }

    public static DatabaseReference getCartReference() {
        return FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getUid())
                .child("cart");
    }

    public static DatabaseReference getCardsReference() {
        return FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getUid())
                .child("card");
    }

    public static DatabaseReference getCompletedOrdersReference(){
        return FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getUid())
                .child("completed-orders");
    }


    public static DatabaseReference getAvailableFravellersReference() {
        return FirebaseDatabase.getInstance().getReference()
                .child("fravellers");
    }
}

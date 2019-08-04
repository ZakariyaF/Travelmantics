package com.zakariyaf.travelmantics;


import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FirebaseUtil {
    public static FirebaseDatabase sFirebaseDatabase;
    public static DatabaseReference sDatabaseReference;
    public static FirebaseAuth sFirebaseAuth;
    public static FirebaseAuth.AuthStateListener sAuthListener;
    public static ArrayList<TravelDeal> sTravelDeals;
    private static FirebaseUtil sFirebaseUtil;

    private FirebaseUtil() {
    }

    public static void openFirebaseReference(String ref) {
        if (sFirebaseUtil == null) {
            sFirebaseUtil = new FirebaseUtil();
            sFirebaseDatabase = FirebaseDatabase.getInstance();
            sFirebaseAuth = FirebaseAuth.getInstance();
            sAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                }
            };
        }
        sTravelDeals = new ArrayList<>();
        sDatabaseReference = sFirebaseDatabase.getReference().child(ref);
    }

    public static void attachListener() {
        sFirebaseAuth.addAuthStateListener(sAuthListener);
    }

    public static void detachListener() {
        sFirebaseAuth.removeAuthStateListener(sAuthListener);
    }

}

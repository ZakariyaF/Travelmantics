package com.zakariyaf.travelmantics;


import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirebaseUtil {

    public static FirebaseDatabase sFirebaseDatabase;
    public static DatabaseReference sDatabaseReference;
    public static FirebaseAuth sFirebaseAuth;
    public static FirebaseAuth.AuthStateListener sAuthListener;
    public static ArrayList<TravelDeal> sTravelDeals;
    private static FirebaseUtil sFirebaseUtil;

    private static final int RC_SIGN_IN = 123;
    private static ListActivity mCallerActivity;

    public static boolean sIsAdmin;
    private FirebaseUtil() {
    }

    public static void openFirebaseReference(String ref, final ListActivity callerActivity) {
        if (sFirebaseUtil == null) {
            sFirebaseUtil = new FirebaseUtil();
            sFirebaseDatabase = FirebaseDatabase.getInstance();
            sFirebaseAuth = FirebaseAuth.getInstance();
            mCallerActivity = callerActivity;
            sAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() == null) {
                        FirebaseUtil.signIn();
                    } else {
                        String userId = firebaseAuth.getUid();
                        checkAdmin(userId);
                    }
                    Toast.makeText
                            (mCallerActivity.getBaseContext(), "Welcome back!", Toast.LENGTH_LONG).show();
                }
            };
        }
        sTravelDeals = new ArrayList<>();
        sDatabaseReference = sFirebaseDatabase.getReference().child(ref);
    }

    private static void checkAdmin(String userId) {
        FirebaseUtil.sIsAdmin = false;
        DatabaseReference ref = sFirebaseDatabase.getReference().child("administrators").child(userId);
        ChildEventListener listener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                FirebaseUtil.sIsAdmin = true;
                mCallerActivity.showMenu();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ref.addChildEventListener(listener);
    }

    private static void signIn() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

// Create and launch sign-in intent
        mCallerActivity.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    public static void attachListener() {
        sFirebaseAuth.addAuthStateListener(sAuthListener);
    }

    public static void detachListener() {
        sFirebaseAuth.removeAuthStateListener(sAuthListener);
    }

}

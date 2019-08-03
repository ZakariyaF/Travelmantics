package com.zakariyaf.travelmantics;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FirebaseUtil {
    public static FirebaseDatabase sFirebaseDatabase;
    public static DatabaseReference sDatabaseReference;
    public static ArrayList<TravelDeal> sTravelDeals;
    private static FirebaseUtil sFirebaseUtil;

    private FirebaseUtil() {
    }

    public static void openFirebaseReference(String ref) {
        if (sFirebaseUtil == null) {
            sFirebaseUtil = new FirebaseUtil();
            sFirebaseDatabase = FirebaseDatabase.getInstance();
        }
        sTravelDeals = new ArrayList<>();
        sDatabaseReference = sFirebaseDatabase.getReference().child(ref);
    }
}

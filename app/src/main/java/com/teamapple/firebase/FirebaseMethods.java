package com.teamapple.firebase;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamapple.models.Notification;
import com.teamapple.models.User;

import java.util.HashMap;
import java.util.Map;

public class FirebaseMethods {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;

    private Context mContext;

    public FirebaseMethods(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mContext = context;
        myRef = FirebaseDatabase.getInstance().getReference();

        if (mAuth.getCurrentUser() != null) {
            userID = mAuth.getCurrentUser().getUid();
        }
    }

    /**
     * Register new auth user by email and password
     *
     * @param email
     * @param password
     * @param user
     */
    public void registerNewEmail(final String email, String password, final User user) {
        mAuth.createUserWithEmailAndPassword(email, password);
        userID = mAuth.getCurrentUser().getUid();

        myRef = myRef.child("users/");
        Map<String, User> dataToAdd = new HashMap<>();
        dataToAdd.put(userID, user);
        myRef.setValue(dataToAdd);
        dataToAdd.clear();
    }

//    public User getCurrentUserData() {
//        userID = mAuth.getCurrentUser().getUid();
//        myRef = myRef.getDatabase().getReference("users/"+userID).addListenerForSingleValueEvent(){
//
//        };
//    }

    public void addNotification(Notification newNot) {
        userID = mAuth.getCurrentUser().getUid();
        myRef = FirebaseDatabase.getInstance().getReference("notifications/"+userID);
        myRef.setValue(newNot);
        Log.d("Add not", "works");
    }
}

package com.teamapple.firebase;

import android.content.Context;
import android.util.Log;
import android.support.annotation.NonNull;

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamapple.models.Notification;
import com.teamapple.models.User;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FirebaseMethods {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String userID;
    User userData = new User();
    private Context mContext;

    public FirebaseMethods(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mContext = context;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
       myRef = mFirebaseDatabase.getReference();

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
        mAuth.signOut();
        mAuth.signInWithEmailAndPassword(email, password);
        userID = mAuth.getCurrentUser().getUid();

        myRef = FirebaseDatabase.getInstance().getReference("users/"+userID);
        myRef.setValue(user);

        mAuth.signOut();
    }

    public ArrayList<Notification> getUserNotifications(){
        userID = mAuth.getCurrentUser().getUid();

        myRef.child("notifications").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Notification notification = dataSnapshot.getValue(Notification.class);
                Log.i("Notification: ", notification.getLabel());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return null;
    }

    public void addNotification(Notification newNot) {
        userID = mAuth.getCurrentUser().getUid();
        myRef = FirebaseDatabase.getInstance().getReference("notifications/"+userID+"/"+new Date().getTime());
        myRef.setValue(newNot);
        Log.d("Add not", "works");
    }

    public User getCurrentUserData() {
        userID = FirebaseAuth.getInstance().getUid();
        Log.d("USER ID IS", userID);
        myRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("DataSnapshot1 is: ", dataSnapshot.toString());
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Log.d("DataSnapshot is: ", dataSnapshot.toString());
                    String key = dataSnapshot.getKey();
                    if(userID.equals(key)){
                        userData = data.getValue(User.class);
                       // Log.d("User1 is: ", userData.toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Log.d("User is: ", userData.toString());
        return userData;
    }
}

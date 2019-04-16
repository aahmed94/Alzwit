package com.teamapple.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamapple.models.EmergencyUser;
import com.teamapple.models.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FirebaseMethods {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
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
        userID = mAuth.getCurrentUser().getUid();

        myRef = myRef.child("users/");
        Map<String, User> users = new HashMap<>();
        users.put(userID, user);
        myRef.setValue(users);
    }
    public User getCurrentUserData() {
        userID = mAuth.getCurrentUser().getUid();

        myRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String key = dataSnapshot.getKey();
                    if(userID.equals(key)){
                        userData = data.getValue(User.class);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return userData;
    }

}

package com.teamapple.alzwit;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.text.format.DateFormat;
import com.teamapple.firebase.FirebaseMethods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamapple.models.User;

import java.util.Date;

public class PassportActivity extends AppCompatActivity {

    //private User user = new User();
    private Context mContext;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users");
    private String userID;
    private FirebaseMethods firebaseMethods ;
    private TextView mFullName,mPhone,mBirtday,mAddress;
    private Button btnEmContact;
    private User user;
    private User userData;
   String hi = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpassport);
        mContext = PassportActivity.this;
        getControls();
       // prepareFirebase();

       user = getCurrentUserData();

       // onClickRegisterButton();
       // setCurrentUserData();
    }

    private void getControls() {
        mAddress = findViewById(R.id.passportActivityAddress);
        mBirtday= findViewById(R.id.passportActivityBirthDate);
        mFullName=findViewById(R.id.passportActivityFullName);
        mPhone=findViewById(R.id.passportActivityTelephone);
        btnEmContact = findViewById(R.id.passportActivityButton);
    }


    private void prepareFirebase() {
        mAuth = FirebaseAuth.getInstance();
        firebaseMethods = new FirebaseMethods(mContext);
    }

    private void onClickRegisterButton() {
        btnEmContact.setOnClickListener(new View.OnClickListener() {
            Uri number = Uri.parse( "tel:" + user.getEmergencyUser().getPhoneNumber());


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL,number);
                startActivity(intent);
            }
        });
    }

    private void setCurrentUserData() {
        String labelFullName = "My name is: ",
        labelAdress = "My adress is: ",
        labelPhone= "My phone number is: ",
        labelBirtday = "My birtday is: ",
        labelButton = "Call ",
       age = getAge(user.getBirtday()),
        emContactName = user.getEmergencyUser().getFullName();


            mFullName.setText(labelFullName + user.getFullName());
            mPhone.setText(labelPhone + user.getPhoneNumber());
            mAddress.setText(labelAdress + user.getAddress());
           mBirtday.setText(labelBirtday + user.getBirtday() + "("+age+")" );
            btnEmContact.setText(labelButton + emContactName);

    }

    private String getAge(Date birtday) {

        int day = Integer.parseInt(DateFormat.format("dd",birtday).toString());
        int month = Integer.parseInt(DateFormat.format("MM",birtday).toString());
        int year = Integer.parseInt(DateFormat.format("yyyy",birtday).toString());

        Calendar userBirtday = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        userBirtday.set(year, month, day);

        int age = today.get(Calendar.YEAR) - userBirtday.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < userBirtday.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }
    public User getCurrentUserData() {
        userID = FirebaseAuth.getInstance().getUid();
        Log.d("USER ID IS", userID);
        myRef.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hi = "Something";
                Log.d("DataSnapshot1 is: ", dataSnapshot.toString());
                    Log.d("DataSnapshot is: ", dataSnapshot.toString());
                       userData = dataSnapshot.getValue(User.class);
                        Log.d("User1 is: ", "We are here");
                setCurrentUserData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

      Log.d("User is: ", hi);
        return userData;
    }

}

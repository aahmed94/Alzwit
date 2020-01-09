package com.teamapple.alzwit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamapple.models.User;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PassportActivity extends AppCompatActivity {
    private Context mContext;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users");
    private String userID;
    private TextView mFullName,mPhone,mBirthday,mAddress;
    private Button btnEmergencyContact;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpassport);
        mContext = PassportActivity.this;
        getControls();
        getCurrentUserData();
    }

    private void getControls() {
        mAddress = findViewById(R.id.passportActivityAddress);
        mBirthday = findViewById(R.id.passportActivityBirthDate);
        mFullName = findViewById(R.id.passportActivityFullName);
        mPhone = findViewById(R.id.passportActivityTelephone);
        btnEmergencyContact = findViewById(R.id.passportActivityButton);
    }

    private void onClickCallButton() {
        btnEmergencyContact.setOnClickListener(new View.OnClickListener() {
            Uri number = Uri.parse( "tel:" + user.getEmergencyUser().getPhoneNumber());
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(intent);
            }
        });
    }

    private void setCurrentUserData(User user) {
        this.user = user;

        String labelFullName = "My name is: ",
        labelAddress = "My address is: ",
        labelPhone= "My phone number is: ",
        labelBirthday = "My birthday is: ",
        labelButton = "Call ",
        emergencyContactName = user.getEmergencyUser().getFullName();

        Date date = user.getBirthday();
        String birthday = date.getDay() + "/" + date.getMonth() + "/" + date.getYear();

            mFullName.setText(labelFullName + user.getFullName());
            mPhone.setText(labelPhone + user.getPhoneNumber());
            mAddress.setText(labelAddress + user.getAddress());
            mBirthday.setText(labelBirthday + birthday + " (My age is: " + calculateBirthday(date) + ")" );
            btnEmergencyContact.setText(labelButton + emergencyContactName);
    }

    public void getCurrentUserData() {
        userID = FirebaseAuth.getInstance().getUid();
        myRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                     User userData = dataSnapshot.getValue(User.class);
                     setCurrentUserData(userData);
                     onClickCallButton();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private String calculateBirthday(Date birthDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthDate);
        LocalDate userBirthday = LocalDate.of(calendar.get(Calendar.YEAR) - 1900, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        LocalDate currentDate = LocalDate.now();

        return String.valueOf(ChronoUnit.YEARS.between(userBirthday, currentDate));
    }
}
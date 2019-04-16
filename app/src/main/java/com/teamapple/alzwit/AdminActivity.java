package com.teamapple.alzwit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import java.time.LocalDate;
import java.time.LocalTime;

public class AdminActivity extends AppCompatActivity {

    private EditText mLabel, mDescription, mDate, mStartTime, mEndTime;
    private String label, description;
    private LocalDate date;
    private LocalTime startTime, endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        getControls();
        getUserInput();
    }

    private void getUserInput() {
        label = mLabel.getText().toString();
        description = mDescription.getText().toString();

    }

    private void getControls() {
        mLabel = findViewById();
        mDescription = findViewById();
        mDate = findViewById();
        mStartTime = findViewById();
        mEndTime = findViewById();
    }
}

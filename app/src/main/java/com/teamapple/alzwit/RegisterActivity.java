package com.teamapple.alzwit;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.teamapple.firebase.FirebaseMethods;
import com.teamapple.models.EmergencyUser;
import com.teamapple.models.User;
import com.teamapple.validators.RegisterGuard;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    private Context mContext;
    private String username, password, confirmPassword, firstName, middleName, lastName, birthday, address, phoneNumber, emFullName, emEmail, emPhoneNumber;
    private EditText mUsername, mPassword, mConfirmPassword, mFirstName, mMiddleName, mLastName, mBirthday, mAddress, mPhoneNumber, mEmFullName, mEmEmail, mEmPhoneNumber;
    private DatePickerDialog mDatePicker;
    private Button btnRegister;
    private ProgressBar mProgressBar;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods firebaseMethods;

    private ArrayList<String> errorMessages;
    private RegisterGuard registerGuard = new RegisterGuard();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getControls();

        mProgressBar.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        firebaseMethods=new FirebaseMethods(mContext);
        setupFirebaseAuth();

        prepareDatePicker();
        onClickRegisterButton();
    }

    private void setupFirebaseAuth() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    //
                } else {
                    //
                }
            }
        };
    }

    private void onClickRegisterButton() {
        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getUserInput();

                if(userInputIsValid())
                {
                    RegisterUser();
                    RedirectToLogin();
                }
                else
                {
                    String errorsToPrint = prepareErrorsForView();
                    Toast.makeText(RegisterActivity.this, errorsToPrint,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void RedirectToLogin() {
        Intent loginView = new Intent(RegisterActivity.this, LoginActivity.class);
        loginView.putExtra("RegisteredEmail", emEmail);
        startActivity(loginView);
    }

    private String prepareErrorsForView() {
        StringBuilder errors = new StringBuilder();

        for (String error:errorMessages) {
            errors.append(error);
            errors.append("\n\r");
        }

        return errors.toString().trim();
    }

    private void RegisterUser() {
        mProgressBar.setVisibility(View.VISIBLE);

        String[] dateParts= birthday.split("/");
        Date birthDate = new Date(Integer.parseInt(dateParts[2]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[0]));
        User user = new User(firstName, middleName, lastName, address, birthDate, phoneNumber, new EmergencyUser(emFullName, emEmail, emPhoneNumber));

        firebaseMethods.registerNewEmail(emEmail, password, user);
    }

    private boolean userInputIsValid() {
        String[] dateParts= birthday.split("/");
        Date birthDate = new Date(Integer.parseInt(dateParts[2]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[0]));

        errorMessages = registerGuard.validateUser(password, firstName, lastName, address, birthDate, phoneNumber, new EmergencyUser(emFullName, emEmail, emPhoneNumber));

        return errorMessages.size()==0;
    }

    private void prepareDatePicker() {
        mBirthday = (EditText) findViewById(R.id.registerActivityDate);
        mBirthday.setInputType(InputType.TYPE_NULL);

        mBirthday.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                mDatePicker = new DatePickerDialog(RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                mBirthday.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + (year));
                            }
                        }, year, month, day);
                mDatePicker.show();
            }
        });
    }

    private void getUserInput() {
        password=mPassword.getText().toString();
        confirmPassword=mConfirmPassword.getText().toString();
        firstName=mFirstName.getText().toString();
        middleName=mMiddleName.getText().toString();
        lastName=mLastName.getText().toString();
        birthday=mBirthday.getText().toString();
        address=mAddress.getText().toString();
        phoneNumber=mPhoneNumber.getText().toString();
        emFullName=mEmFullName.getText().toString();
        emEmail=mEmEmail.getText().toString();
        emPhoneNumber=mEmPhoneNumber.getText().toString();
    }

    private void getControls() {
        btnRegister = (Button) findViewById(R.id.registerButton);
        mPassword = (EditText) findViewById(R.id.registerActivityInputPassword);
        mConfirmPassword = (EditText) findViewById(R.id.registerActivityInputPasswordConfirm);
        mFirstName = (EditText) findViewById(R.id.registerActivityFirstName);
        mMiddleName = (EditText) findViewById(R.id.registerActivityMiddleName);
        mLastName = (EditText) findViewById(R.id.registerActivityLastName);
        mBirthday = (EditText) findViewById(R.id.registerActivityDate);
        mAddress = (EditText) findViewById(R.id.registerActivityAddress);
        mPhoneNumber = (EditText) findViewById(R.id.registerActivityPhoneNumber);
        mEmFullName = (EditText) findViewById(R.id.registerActivityEmergencyName);
        mEmEmail = (EditText) findViewById(R.id.registerActivityEmail);
        mEmPhoneNumber = (EditText) findViewById(R.id.registerActivityEmergencyPhoneNumber);
        mProgressBar = (ProgressBar) findViewById(R.id.registerActivityProgressBar);
    }
}

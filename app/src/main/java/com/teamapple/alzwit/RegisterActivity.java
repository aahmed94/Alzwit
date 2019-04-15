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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamapple.firebase.FirebaseMethods;
import com.teamapple.models.EmergencyUser;
import com.teamapple.models.User;
import com.teamapple.validators.RegisterGuard;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    private Context mContext;
    private String password, confirmPassword, firstName, middleName, lastName, birthday, address, phoneNumber, emFullName, emEmail, emPhoneNumber;
    private EditText mPassword, mConfirmPassword, mFirstName, mMiddleName, mLastName, mBirthday, mAddress, mPhoneNumber, mEmFullName, mEmEmail, mEmPhoneNumber;
    private DatePickerDialog mDatePicker;
    private Button btnRegister;
    private ProgressBar mProgressBar;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods firebaseMethods;

    private ArrayList<String> errorMessages;
    private RegisterGuard registerGuard = new RegisterGuard();
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = RegisterActivity.this;

        getControls();
        hideProgressBar();
        prepareFirebase();
        prepareDatePicker();
        onClickRegisterButton();
    }

    /**
     * Instanciate the firebase fields.
     */
    private void prepareFirebase() {
        mAuth = FirebaseAuth.getInstance();
        firebaseMethods = new FirebaseMethods(mContext);
        setupFirebaseAuth();
    }

    /**
     * Hides the progress bar.
     */
    private void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * Checks the user's state
     */
    private void setupFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                } else {
                    // User is signed out
                }
            }
        };
    }

    /**
     * On click on register button => check input and redirect to login or show errors.
     */
    private void onClickRegisterButton() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInput();

                if (userInputIsValid()) {
                    registerUser();
                    redirectToLogin();
                } else {
                    String errorsToPrint = prepareErrorsForView();
                    Toast.makeText(RegisterActivity.this, errorsToPrint,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Redirects to the login screen
     */
    private void redirectToLogin() {
        Intent loginView = new Intent(RegisterActivity.this, LoginActivity.class);
        loginView.putExtra("RegisteredEmail", emEmail);
        startActivity(loginView);
    }

    /**
     * Prepare the list of errors for print => as single string
      * @return List of errors as single string
     */
    private String prepareErrorsForView() {
        StringBuilder errors = new StringBuilder();

        for (String error : errorMessages) {
            errors.append(error);
            errors.append("\n\r");
        }

        return errors.toString().trim();
    }

    /**
     * Register user
     */
    private void registerUser() {
        showProgressBar();

        User user = new User(firstName, middleName, lastName, address, getDate(birthday), phoneNumber, new EmergencyUser(emFullName, emEmail, emPhoneNumber));
        firebaseMethods.registerNewEmail(emEmail, password, user);
    }

    /**
     * Get Date object from string in format dd/MM/yyyy
     * @param birthday as stirng
     * @return Date of string
     */
    private Date getDate(String birthday) {
        String[] dateParts = birthday.split("/");
        if (dateParts.length == 3) {
            return new Date(Integer.parseInt(dateParts[2]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[0]));
        }

        return new Date();
    }

    /**
     * Show the progress bar.
     */
    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Check if the user's input is valid
     * @return whether the input is valid
     */
    private boolean userInputIsValid() {
        errorMessages = registerGuard.validateUser(password, confirmPassword, firstName, lastName, address, getDate(birthday), phoneNumber, new EmergencyUser(emFullName, emEmail, emPhoneNumber));

        return errorMessages.size() == 0;
    }

    /**
     * We prepare the date picker to be ready to pop up when birtday control is clicked
     */
    private void prepareDatePicker() {
        mBirthday = (EditText) findViewById(R.id.registerActivityDate);
        mBirthday.setInputType(InputType.TYPE_NULL);

        mBirthday.setOnClickListener(new View.OnClickListener() {

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

    /**
     * Get the user's input
     */
    private void getUserInput() {
        password = mPassword.getText().toString();
        confirmPassword = mConfirmPassword.getText().toString();
        firstName = mFirstName.getText().toString();
        middleName = mMiddleName.getText().toString();
        lastName = mLastName.getText().toString();
        birthday = mBirthday.getText().toString();
        address = mAddress.getText().toString();
        phoneNumber = mPhoneNumber.getText().toString();
        emFullName = mEmFullName.getText().toString();
        emEmail = mEmEmail.getText().toString();
        emPhoneNumber = mEmPhoneNumber.getText().toString();
    }

    /**
     * Get the view's controls
     */
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

//package com.teamapple.alzwit;
//
//import android.app.DatePickerDialog;
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.text.InputType;
//import android.view.View;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.ProgressBar;
//
//import com.teamapple.models.EmergencyUser;
//import com.teamapple.validators.RegisterGuard;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//
//public class RegisterActivity extends AppCompatActivity {
//    private Context mContext;
//    private String username, password, confirmPassword, firstName, middleName, lastName, birthday, address, phoneNumber, emFullName, emEmail, emPhoneNumber;
//    private EditText mUsername, mPassword, mConfirmPassword, mFirstName, mMiddleName, mLastName, mBirthday, mAddress, mPhoneNumber, mEmFullName, mEmEmail, mEmPhoneNumber;
//    private DatePickerDialog mDatePicker;
//    private Button btnRegister;
//    private ProgressBar mProgressBar;
//
//    //firebase
//    private FirebaseAuth mAuth;
//    private FirebaseAuth.AuthStateListener mAuthListener;
//    private FirebaseMethods firebaseMethods;
//
//    private ArrayList<String> errorMessages;
//    private RegisterGuard registerGuard = new RegisterGuard();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//
//        mContext=RegisterActivity.this;
//        firebaseMethods=new FirebaseMethods(mContext);
//        setupFirebaseAuth();
//
//        prepareDatePicker();
//        onClickRegisterButton();
//    }
//
//    private void setupFirebaseAuth() {
//        mAuth = FirebaseAuth.getInstance();
//
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//
//                if (user != null) {
//                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                } else {
//                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                }
//                // ...
//            }
//        };
//    }
//
//    private void onClickRegisterButton() {
//        btnRegister.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                getUserInput();
//
//                if(userInputIsValid())
//                {
//                    RegisterUser();
//                }
//                else
//                {
//                    //Wrong user input logic
//                }
//            }
//        });
//    }
//
//    private void RegisterUser() {
//
//    }
//
//    private boolean userInputIsValid() {
//        String[] dateParts= birthday.split("/");
//        Date birthDate = new Date(Integer.parseInt(dateParts[2]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[0]));
//
//        errorMessages = registerGuard.validateUser(username, password, firstName, lastName, address, birthDate, phoneNumber, new EmergencyUser(emFullName, emEmail, emPhoneNumber));
//
//        return errorMessages.size()==0;
//    }
//
//    private void prepareDatePicker() {
//        mBirthday = (EditText) findViewById(R.id.RegisterActivityBirthday);
//        mBirthday.setInputType(InputType.TYPE_NULL);
//
//        mBirthday.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//
//                final Calendar cldr = Calendar.getInstance();
//                int day = cldr.get(Calendar.DAY_OF_MONTH);
//                int month = cldr.get(Calendar.MONTH);
//                int year = cldr.get(Calendar.YEAR);
//                // date picker dialog
//                mDatePicker = new DatePickerDialog(RegisterActivity.this,
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                                mBirthday.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                            }
//                        }, year, month, day);
//                mDatePicker.show();
//            }
//        });
//    }
//
//    private void getUserInput() {
//        username=mUsername.getText().toString();
//        password=mPassword.getText().toString();
//        confirmPassword=mConfirmPassword.getText().toString();
//        firstName=mFirstName.getText().toString();
//        middleName=mMiddleName.getText().toString();
//        lastName=mLastName.getText().toString();
//        birthday=mBirthday.getText().toString();
//        address=mAddress.getText().toString();
//        phoneNumber=mPhoneNumber.getText().toString();
//        emFullName=mEmFullName.getText().toString();
//        emEmail=mEmEmail.getText().toString();
//        emPhoneNumber=mEmPhoneNumber.getText().toString();
//    }
//}
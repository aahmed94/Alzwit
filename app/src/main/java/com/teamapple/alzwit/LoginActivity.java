package com.teamapple.alzwit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamapple.firebase.FirebaseMethods;
import com.teamapple.models.User;
import com.teamapple.validators.LoginGuard;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private Context mContext;
    private String email, password;
    private EditText mEmail, mPassword;
    private Button btnLogin;
    private ProgressBar mProgressBar;
    private TextView register;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods firebaseMethods;

    private LoginGuard loginGuard = new LoginGuard();
    private ArrayList<String> errorMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = LoginActivity.this;
        FirebaseApp.initializeApp(mContext);

        getControls();
        hideProgressBar();
        prepareFirebase();
        onClickLoginButton();
        onClickRegisterButton();
        loadRecentRegisterData();
    }

    private void loadRecentRegisterData() {
        Intent intent = getIntent();
        String email = intent.getStringExtra("RegisteredEmail");

        mEmail.setText(email);
    }

    /**
     * Hides the progress bar.
     */
    private void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * Shows the progress bar.
     */
    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Instance the firebase fields
     */
    private void prepareFirebase() {
        mAuth = FirebaseAuth.getInstance();
        firebaseMethods = new FirebaseMethods(mContext);
       setupFirebaseAuth();
    }

    /**
     * Checks the state of the user
     */
    private void setupFirebaseAuth() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    redirectToMainView();
                }
            }
        };
    }

    /**
     * Connects the fields to the controls in the view.
     */
    private void getControls() {
        mEmail = (EditText) findViewById(R.id.inputEmail);
        mPassword = (EditText) findViewById(R.id.inputPassword);
        btnLogin = (Button) findViewById(R.id.loginButton);
        mProgressBar = (ProgressBar) findViewById(R.id.loginActivityProgressBar);
        register = (TextView) findViewById(R.id.createNewUserTxt);
    }

    /**
     * On click on login button => check user input and then register or show errors.
     */
    private void onClickLoginButton() {
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getUserInput();
                if (userInputIsValid()) {
                    showProgressBar();
                    logInUser(email, password);
                } else {
                    String errorsToPrint = prepareErrorsForView();
                    Toast.makeText(LoginActivity.this, errorsToPrint,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Prepare the list of errors to print
     *
     * @return The list of errors as single string
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
     * Redirect to the main screen without option to go back.
     */
    private void redirectToMainView() {
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Check if the user input is valid.
     *
     * @return if the user input is valid.
     */
    private boolean userInputIsValid() {
        errorMessages = loginGuard.validateLogin(email, password);

        return errorMessages.size() == 0;
    }

    /**
     * On click on the register button => load register screen
     */
    private void onClickRegisterButton() {
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Get the input from the view's controls.
     */
    private void getUserInput() {
        email = mEmail.getText().toString();
        password = mPassword.getText().toString();
    }

    /**
     * Log In the user and redirect to main screen or show login errors.
     *
     * @param email    The user's email
     * @param password The user's password
     */
    private void logInUser(final String email, final String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            hideProgressBar();
                            redirectToMainView();
                        } else {
                            Toast.makeText(LoginActivity.this, "Wrong credentials.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        setupFirebaseAuth();
                    }
                });
    }

}

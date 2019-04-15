package com.teamapple.alzwit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.teamapple.firebase.FirebaseMethods;
import com.teamapple.validators.LoginGuard;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private Context mContext;
    private String email,password;
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
        mContext=LoginActivity.this;

        getControls();
        hideProgressBar();
        prepareFirebase();
        onClickLoginButton();
        onClickRegisterButton();
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void prepareFirebase() {
        mAuth = FirebaseAuth.getInstance();
        firebaseMethods = new FirebaseMethods(mContext);
        setupFirebaseAuth();
    }

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

    private void getControls() {
        mEmail =  (EditText) findViewById(R.id.inputEmail);
        mPassword = (EditText) findViewById(R.id.inputPassword);
        btnLogin =  (Button) findViewById(R.id.loginButton);
        mProgressBar = (ProgressBar) findViewById(R.id.loginActivityProgressBar);
        register = (TextView) findViewById(R.id.createNewUserTxt);
    }

    private void onClickLoginButton() {
        btnLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getUserInput();
                if(userInputIsValid()) {
                    logInUser(email, password);
                }
                else {
                    String errorsToPrint = prepareErrorsForView();
                    Toast.makeText(LoginActivity.this, errorsToPrint,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private String prepareErrorsForView() {
        StringBuilder errors = new StringBuilder();

        for (String error:errorMessages) {
            errors.append(error);
            errors.append("\n\r");
        }

        return errors.toString().trim();
    }

    private void redirectToMainView() {
        Intent intent = new Intent(mContext,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private boolean userInputIsValid() {
        errorMessages = loginGuard.validateLogin(email, password);

        return errorMessages.size()==0;
    }

    private void onClickRegisterButton() {
        register.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getUserInput() {
        email = mEmail.getText().toString();
        password = mPassword.getText().toString();
    }

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

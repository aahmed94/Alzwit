package com.teamapple.alzwit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.teamapple.firebase.FirebaseMethods;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext=LoginActivity.this;
        //firebaseMethods=new FirebaseMethods(mContext);
       // setupFirebaseAuth();

        onClickLoginButton();
    }
    private Context mContext;
    private String email,password;
    private EditText mEmail, mPassword;
    private Button btnLogin;
    private ProgressBar mProgressBar;



    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //private FirebaseMethods firebaseMethods;


   /* private void setupFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();

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
*/

    private void onClickLoginButton() {
        btnLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getUserInput();

                if(userInputIsCorrect())
                {
                    LoginUser();
                }
                else
                {
                    //Wrong user input logic
                }
            }
        });
    }

    private void LoginUser() {
        //to do
    }

    private boolean userInputIsCorrect() {
    ///to do
        return true;
    }

    private void getUserInput() {
        email=mEmail.getText().toString();
        password=mPassword.getText().toString();
    }
}

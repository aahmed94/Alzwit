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

import java.util.concurrent.Executor;


public class LoginActivity extends AppCompatActivity {

    private Context mContext;
    private String email,password;
    private EditText mEmail, mPassword;
    private Button btnLogin;
    private ProgressBar mProgressBar;
    private TextView Register;



    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods firebaseMethods;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setControls();
        mContext=LoginActivity.this;
        firebaseMethods=new FirebaseMethods(mContext);
        mProgressBar.setVisibility(View.INVISIBLE);
        setupFirebaseAuth();


    }




    private void setupFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {

                    Intent intent = new Intent(mContext,MainActivity.class);

                    startActivity(intent);

                } else {

                    onClickLoginButton();
                    onClickRegisterButton();
                }
            }
        };
    }


    private void setControls() {
        mEmail =  findViewById(R.id.inputEmail);
        mPassword =findViewById(R.id.inputPassword);
        btnLogin =  findViewById(R.id.loginButton);
        mProgressBar =findViewById(R.id.loginActivityProgressBar);
        Register =  findViewById(R.id.createNewUserTxt);

    }
    private void onClickLoginButton() {
        btnLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getUserInput();


                LogInUser(email ,password);


            }
        });
    }
    private void onClickRegisterButton() {
        Register.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,RegisterActivity.class);

                startActivity(intent);

            }
        });
    }

    private void getUserInput() {
        email=mEmail.getText().toString();
        password=mPassword.getText().toString();
    }
    private void LogInUser(final String email, final String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            mProgressBar.setVisibility(View.INVISIBLE);

                        } else {
                            mProgressBar.setVisibility(View.VISIBLE);
                        }
                        setupFirebaseAuth();
                    }
                });


    }
}

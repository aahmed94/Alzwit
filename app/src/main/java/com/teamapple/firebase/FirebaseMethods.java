//package com.teamapple.firebase;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//
//public class FirebaseMethods {
//    private FirebaseAuth mAuth;
//    private FirebaseAuth.AuthStateListener mAuthListener;
//    private String userID;
//
//    private Context mContext;
//
//    public FirebaseMethods(Context context) {
//        mAuth = FirebaseAuth.getInstance();
//        mContext = context;
//
//        if(mAuth.getCurrentUser() != null){
//            userID = mAuth.getCurrentUser().getUid();
//        }
//    }
//
//    public void registerNewEmail(final String email, String password, final String username){
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        // If sign in fails, display a message to the user. If sign in succeeds
//                        // the auth state listener will be notified and logic to handle the
//                        // signed in user can be handled in the listener.
//                        if (!task.isSuccessful()) {
//                            Toast.makeText(mContext, R.string.auth_failed,
//                                    Toast.LENGTH_SHORT).show();
//
//                        }
//                        else if(task.isSuccessful()){
//                            userID = mAuth.getCurrentUser().getUid();
//                            Log.d(TAG, "onComplete: Authstate changed: " + userID);
//                        }
//                    }
//                });
//    }
//}

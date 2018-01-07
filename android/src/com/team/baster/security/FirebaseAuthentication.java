package com.team.baster.security;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthentication {
    private static final String TAG = "FirebaseAuthentication";

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    public static FirebaseAuthentication auth;
    private Activity activity;

    public FirebaseAuthentication(Activity activity) {
        this.activity = activity;
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    public void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    }
                });
    }

    public void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                    }
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Auth failed");
                    }
                });
    }

    public void signOut() {
        mAuth.signOut();
    }

    public void sendEmailVerification() {
        FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification();
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }
}


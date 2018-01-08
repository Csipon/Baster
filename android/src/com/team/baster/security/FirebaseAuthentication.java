package com.team.baster.security;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
        Log.d(TAG, "in create method with params :" + email + password);
        System.out.println("in create method with params :" + email + password);

        Task<AuthResult> task = mAuth.createUserWithEmailAndPassword(email, password);
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Log.d(TAG, "createUserWithEmail:success");
                    System.out.println("createUserWithEmail:success");

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    System.out.println("createUserWithEmail:failure");
                }

//            }
//        });
    }

    public void signIn(String email, String password) {

        Log.d(TAG, "signIn:" + email);
        Task<AuthResult> task = mAuth.signInWithEmailAndPassword(email, password);
        if (task.isSuccessful()) {
            Log.d(TAG, "signInWithEmail:success");

        } else {
            Log.w(TAG, "signInWithEmail:failure", task.getException());
        }

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


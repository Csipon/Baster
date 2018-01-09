package com.team.baster.security;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.team.baster.domain.BasterGame;

public class FirebaseAuthentication {
    private static final String TAG = "FirebaseAuthentication";

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    public static FirebaseAuthentication auth;
    private Activity activity;
    private BasterGame basterGame;

    public FirebaseAuthentication(Activity activity, BasterGame basterGame) {
        this.activity = activity;
        this.basterGame = basterGame;
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    public Task<AuthResult> createAccount(String email, String password) {
        Log.d(TAG, "============================== createAccount:" + email);
        Task<AuthResult> userWithEmailAndPassword = mAuth.createUserWithEmailAndPassword(email, password);
        Log.d(TAG, "============================== createAccount: END");
        return userWithEmailAndPassword;
    }

    public Task<AuthResult> signIn(String email, String password) {

        Log.d(TAG, "============================== signIn:" + email);
        Task<AuthResult> authResultTask = mAuth.signInWithEmailAndPassword(email, password);
        Log.d(TAG, "============================== signIn: END");
        return authResultTask;
    }

    public void signOut() {
        mAuth.signOut();
        Log.w(TAG, "User is signOut");
    }

    public void sendEmailVerification() {
        FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification();
    }

    public FirebaseUser getCurrentUser() {
        if (currentUser == null){
            currentUser = mAuth.getCurrentUser();
        }
        return currentUser;
    }
}


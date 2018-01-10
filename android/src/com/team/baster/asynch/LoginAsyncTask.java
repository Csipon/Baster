package com.team.baster.asynch;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.team.baster.R;
import com.team.baster.dialog.ActionResolverImpl;
import com.team.baster.security.FirebaseAuthentication;

import static android.content.ContentValues.TAG;

/**
 * Created by Smeet on 10.01.2018.
 */

public class LoginAsyncTask extends AsyncTask {

    private static final String TAG = "FirebaseAuthentication";
    private String email;
    private String password;
    private ActionResolverImpl actionResolver;
    private ProgressDialog progressDialog;
    private Context context;
    private Dialog dialog;
    private FirebaseAuthentication auth;
    private boolean check;

    public LoginAsyncTask(String email, String password, ActionResolverImpl actionResolver, Context context, Dialog dialog) {
        this.email = email;
        this.password = password;
        this.actionResolver = actionResolver;
        this.context = context;
        this.dialog = dialog;
        auth = FirebaseAuthentication.auth;

    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_bar);

    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Task<AuthResult> authTask = authentication(email, password);
        Log.d(TAG, "Auth task = " + authTask);

        if (authTask == null){
            Log.w(TAG, "signInWithEmail:failure");
            progressDialog.dismiss();//loader
            actionResolver.showToast("Error");

        }else {
            authTask.addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        check = true;
                        progressDialog.dismiss();
                        dialog.dismiss();
                        actionResolver.showToast("Hello nickname");
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        progressDialog.dismiss();
                        actionResolver.showToast("Error");

                    }
                }
            });
        }
        return null;

    }

    @Override
    protected void onPostExecute(Object o) {
//        if(!check) {
//            actionResolver.showToast("Something went wrong");
//        }
        super.onPostExecute(o);
    }

    public Task<AuthResult> authentication(String email, String password){

        if (auth.getCurrentUser() == null){
            Log.d(TAG, "Try to login");
            Log.d(TAG, "Email = " + email + ", password = " + password);

            if(email != null && password != null && email.length() > 3 && password.length() >= 8) {
                Log.d(TAG, "Try to enter credentials");
                return auth.signIn(email, password);
            }
        }
        return null;
    }
}

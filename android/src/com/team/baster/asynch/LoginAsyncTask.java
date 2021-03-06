package com.team.baster.asynch;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.team.baster.AndroidInstanceHolder;
import com.team.baster.R;
import com.team.baster.dialog.ActionResolverImpl;
import com.team.baster.security.FirebaseAuthentication;

/**
 * Created by Smeet on 10.01.2018.
 */

public class LoginAsyncTask extends AsyncTask {

    private static final String TAG = "FirebaseAuthentication";
    private String email;
    private String password;
    private ActionResolverImpl actionResolver;
    private ProgressDialog progressDialog;
    private Dialog dialog;
    private FirebaseAuthentication auth;

    public LoginAsyncTask(String email, String password, ActionResolverImpl actionResolver, Dialog dialog) {
        this.email = email;
        this.password = password;
        this.actionResolver = actionResolver;
        this.dialog = dialog;
        auth = AndroidInstanceHolder.getAuth();

    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(actionResolver.context);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_bar);

    }

    @Override
    protected Object doInBackground(Object[] objects) {
        if (!validateCredantials(email, password)){
            Log.w(TAG, "signInWithEmail:failure");
            progressDialog.dismiss();//loader
            actionResolver.showToast("Error");

        }else {
            authentication(email, password).addOnCompleteListener(AndroidInstanceHolder.getAndroidLauncher(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        progressDialog.dismiss();
                        dialog.dismiss();
                        actionResolver.showToast("Hello " + task.getResult().getUser().getEmail());
                    } else {
                        progressDialog.dismiss();
                        if (task.getException() != null) {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            actionResolver.showToast(task.getException().getMessage());
                        }else {
                            actionResolver.showToast("Error");
                        }

                    }
                }
            });
        }
        return objects;
    }

    @Override
    protected void onPostExecute(Object o) {

        super.onPostExecute(o);
    }

    private Task<AuthResult> authentication(String email, String password){
        Log.d(TAG, "Email = " + email + ", password = " + password);
        return auth.signIn(email, password);
    }

    private boolean validateCredantials(String email, String password){
        if(email != null && password != null && email.length() > 3 && password.length() >= 8) {
            Log.d(TAG, "Credentials is correct");
            return true;
        }else {
            Log.d(TAG, "Bad credentials");
            return false;
        }
    }
}

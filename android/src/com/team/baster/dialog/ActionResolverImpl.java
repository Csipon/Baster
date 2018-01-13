package com.team.baster.dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.team.baster.AndroidInstanceHolder;
import com.team.baster.AndroidLauncher;
import com.team.baster.R;
import com.team.baster.asynch.LoginAsyncTask;
import com.team.baster.asynch.RegistrationAsyncTask;
import com.team.baster.screens.MenuScreen;
import com.team.baster.service.PlayerService;
import com.team.baster.service.ScoreService;
import com.team.baster.service.ServiceFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Smeet on 31.10.2017.
 */

public class ActionResolverImpl implements ActionResolver {
    private static final String TAG = "AUTHENTICATION";

    private Handler handler;
    private static ScoreService scoreService = ServiceFactory.getScoreService();
    public AndroidLauncher context = AndroidInstanceHolder.getAndroidLauncher();
    private EditText emailEdit;
    private EditText passwordEdit;
    public MenuScreen menuScreen;
    private Dialog dialogLogin;
    private PlayerService playerService = ServiceFactory.getPlayerService();

    public ActionResolverImpl() {
        handler = new Handler();
    }

    public void showDialogWithBestScore() {

//        context.startActivity(new Intent(context, MyListActivity.class));

        handler.post(new Runnable() {
            @Override
            public void run() {

                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                dialog.setContentView(R.layout.dialog_layout);

                List<Long> lastBest = scoreService.readLastBestScore();
                ArrayList<Long> scores = new ArrayList<>();
                if(lastBest.size() != 0) {
                    if(lastBest.size() < 10) {
                        for(Long score : lastBest) {
                            scores.add(score);
                        }
                    } else {
                        for (int i = 0; i < 10; i++) {
                            scores.add(lastBest.get(i));
                        }
                    }
                }

                ListView lv = dialog.findViewById(R.id.lv);

                MyAdapter myAdapter = new MyAdapter(context, scores);
                lv.setAdapter(myAdapter);

                dialog.show();
            }
        });
    }

    public void showToast(final CharSequence text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast toast = new Toast(context);
                toast.setGravity(Gravity.TOP, 0, 0);

                View view = View.inflate(context, R.layout.toast_message, context.findViewById(R.id.toast_layout_wrapper));
                toast.setView(view);
                toast.setDuration(Toast.LENGTH_SHORT);
                ((TextView) toast.getView().findViewById(R.id.toastMessage)).setText(text);
                toast.show();
            }
        });
    }

    @Override
    public void showDialog() {
        handler.post(() -> {
            Dialog dialog = new Dialog(context);
            dialog.setTitle("Available soon");
            dialog.show();
        });
    }

    @Override
    public void showDialogLogin(MenuScreen menuScreen) {
        handler.post(() -> {
            this.menuScreen = menuScreen;
            dialogLogin = new Dialog(context);
            dialogLogin.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogLogin.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialogLogin.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            dialogLogin.setContentView(R.layout.dialog_login);
            dialogLogin.setCancelable(false);
            dialogLogin.show();

            Button registrationButton = dialogLogin.findViewById(R.id.registration);
            Button loginButton = dialogLogin.findViewById(R.id.login);

            registrationButton.setOnClickListener(RegistrationClickListener(dialogLogin));
            loginButton.setOnClickListener(LoginClickListener(dialogLogin));

        });

    }

    public void dissmisLoginDialog() {
        dialogLogin.dismiss();
    }

    @SuppressWarnings("unchecked")
    private View.OnClickListener RegistrationClickListener(final Dialog dialog) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emailEdit = dialog.findViewById(R.id.email);
                passwordEdit = dialog.findViewById(R.id.password);

                if(playerService.validatePlayerName(getEmail())) {
                    new RegistrationAsyncTask(getEmail(), getPassword(), ActionResolverImpl.this, dialog).execute();
                } else {
                    //dialog.findViewById(R.id.Error).setVisibility(View.VISIBLE);
                    showToast("Error");
                }
            }
        };
    }



    @SuppressWarnings("unchecked")
    private View.OnClickListener LoginClickListener(final Dialog dialog) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emailEdit = dialog.findViewById(R.id.email);
                passwordEdit = dialog.findViewById(R.id.password);

                if(playerService.validatePlayerName(getEmail())) {
                    new LoginAsyncTask(getEmail(), getPassword(), ActionResolverImpl.this, dialog).execute();

                } else {
                    //dialog.findViewById(R.id.Error).setVisibility(View.VISIBLE);
                    showToast("Error");
                }
            }
        };
    }

    private String getEmail() {
        return emailEdit.getText().toString();
    }

    private String getPassword() {
        return passwordEdit.getText().toString();
    }
}

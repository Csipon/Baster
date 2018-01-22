package com.team.baster.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.team.baster.AndroidInstanceHolder;
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

public class ActionResolverImpl implements ActionResolver{
    private static final String TAG = "AUTHENTICATION";

    private Handler handler;
    private static ScoreService scoreService = ServiceFactory.getScoreService();
    public Context context;
    private EditText emailEdit;
    private EditText passwordEdit;
    public MenuScreen menuScreen;
    private Dialog dialogLogin;
    private Dialog dialogSetting;
    private PlayerService playerService = ServiceFactory.getPlayerService();

    public ActionResolverImpl(Context context) {
        this.context = context;
        init();
    }

    private void init(){
        if (handler == null) {
            AndroidInstanceHolder.getAndroidLauncher().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    handler = new Handler();
                }
            });
        }
    }
    public void showDialogWithBestScore() {
        init();
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
        init();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast toast = new Toast(context);
                toast.setGravity(Gravity.TOP, 0, 0);

                View view = View.inflate(context, R.layout.toast_message, AndroidInstanceHolder.getAndroidLauncher().findViewById(R.id.toast_layout_wrapper));
                toast.setView(view);
                toast.setDuration(Toast.LENGTH_SHORT);
                ((TextView) toast.getView().findViewById(R.id.toastMessage)).setText(text);
                toast.show();
            }
        });
    }

    @Override
    public void showDialog() {
        init();
        handler.post(() -> {
            Dialog dialog = new Dialog(context);
            dialog.setTitle("Available soon");
            dialog.show();
        });
    }


    @Override
    public void showDialogLogin(MenuScreen menuScreen) {
        init();
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

            registrationButton.setOnClickListener(registrationClickListener(dialogLogin));
            loginButton.setOnClickListener(loginClickListener(dialogLogin));

        });

    }

    @Override
    public void showDialogSetting(MenuScreen menuScreen) {
        init();
        handler.post(() -> {
            this.menuScreen = menuScreen;
            dialogSetting = new Dialog(context);
            dialogSetting.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogSetting.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialogSetting.getWindow().getAttributes().windowAnimations = R.style.DialogThemeSide;
            dialogSetting.setContentView(R.layout.dialog_settings);
            dialogSetting.setCancelable(true);
            dialogSetting.show();

            Button btnLogout = dialogSetting.findViewById(R.id.logout);
            btnLogout.setOnClickListener(logoutClickListener(menuScreen));



        });
    }

    @SuppressWarnings("unchecked")
    private View.OnClickListener registrationClickListener(final Dialog dialog) {
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
    private View.OnClickListener logoutClickListener(final MenuScreen menuScreen) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidInstanceHolder.getAuth().signOut();
                AndroidInstanceHolder.getAuth().getCurrentUser();
                AndroidInstanceHolder.getActionResolverAndroid().showToast("Successfully logged out");
                dialogSetting.dismiss();
                AndroidInstanceHolder.getActionResolverAndroid().showDialogLogin(menuScreen);
            }
        };
    }



    @SuppressWarnings("unchecked")
    private View.OnClickListener loginClickListener(final Dialog dialog) {
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

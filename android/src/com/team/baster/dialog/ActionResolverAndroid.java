package com.team.baster.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.team.baster.R;
import com.team.baster.screens.MenuScreen;
import com.team.baster.service.PlayerService;
import com.team.baster.service.ScoreService;
import com.team.baster.service.ServiceFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Smeet on 31.10.2017.
 */

public class ActionResolverAndroid implements ActionResolver {

    private Handler handler;
    private static ScoreService scoreService = ServiceFactory.getScoreService();
    private Context context;
    EditText nameEdit;
    EditText passwordEdit;
    private MenuScreen menuScreen;
    private Dialog dialogLogin;
    private PlayerService playerService = ServiceFactory.getPlayerService();

    private ArrayList<String> myList = new ArrayList<>();

    public ActionResolverAndroid(Context context) {
        handler = new Handler();
        this.context = context;
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
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
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

    private View.OnClickListener RegistrationClickListener(final Dialog dialog) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameEdit = dialog.findViewById(R.id.nickname);
                passwordEdit = dialog.findViewById(R.id.password);

                myList = new ArrayList<>();
                String login = nameEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                //TODO valid for password
                if(playerService.validatePlayerName(login)) {
                    myList.add(0, login);
                    myList.add(1, password);

                    if(menuScreen.registration(myList)) {

                    } else {
                        ((TextView)dialog.findViewById(R.id.Error)).setText("Something went wrong");
                        ((TextView)dialog.findViewById(R.id.Error)).setVisibility(View.VISIBLE);
                    }
                } else {
                    dialog.findViewById(R.id.Error).setVisibility(View.VISIBLE);
                }
            }
        };
    }

    private View.OnClickListener LoginClickListener(final Dialog dialog) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameEdit = dialog.findViewById(R.id.nickname);
                passwordEdit = dialog.findViewById(R.id.password);

                System.out.println("in login click listener");
                myList = new ArrayList<>();
                String login = nameEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                //TODO valid for password
                if(playerService.validatePlayerName(login)) {
                    myList.add(0, login);
                    myList.add(1, password);

                    if(menuScreen.authentication(myList)) {

                    } else {
                        ((TextView)dialog.findViewById(R.id.Error)).setText("Something went wrong");
                        ((TextView)dialog.findViewById(R.id.Error)).setVisibility(View.VISIBLE);
                    }

                } else {
                    dialog.findViewById(R.id.Error).setVisibility(View.VISIBLE);
                }
            }
        };
    }
}

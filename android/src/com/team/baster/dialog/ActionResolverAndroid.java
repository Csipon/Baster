package com.team.baster.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.badlogic.gdx.utils.Array;
import com.team.baster.R;
import com.team.baster.service.PlayerService;
import com.team.baster.service.ScoreService;
import com.team.baster.service.ServiceFactory;
import com.team.baster.service.impl.PlayerServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static com.team.baster.GameConstants.DEFAULT_PLAYER_NAME;

/**
 * Created by Smeet on 31.10.2017.
 */

public class ActionResolverAndroid implements ActionResolver {
//zdzichorowerzysta
    private Handler handler;
    private static ScoreService scoreService = ServiceFactory.getScoreService();
    private Context context;
    EditText nameEdit;
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

                Array<Long> lastBest = scoreService.readLastBestScore();
                ArrayList<Long> scores = new ArrayList<>();
                if(lastBest.size != 0) {
                    if(lastBest.size < 10) {
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
        handler.post(new Runnable() {
            @Override
            public void run() {
                Dialog dialog = new Dialog(context);
                dialog.setTitle("Available soon");
                dialog.show();
            }
        });
    }

    @Override
    public List<String> showDialogLogin() {

        handler.post(new Runnable() {
            @Override
            public void run() {

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                dialog.setContentView(R.layout.dialog_login);
                dialog.setCancelable(false);
                dialog.show();

                Button button = dialog.findViewById(R.id.Go);

                button.setOnClickListener(myClickListener(dialog));

            }
        });

        return myList;
    }

    private View.OnClickListener myClickListener(final Dialog dialog) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameEdit = dialog.findViewById(R.id.Nickname);

                String nickName = nameEdit.getText().toString();
                if(playerService.validatePlayerName(nickName)) {
                    myList.add(nickName);
                    dialog.dismiss();
                } else {
                    dialog.findViewById(R.id.Error).setVisibility(View.VISIBLE);
                }
            }
        };
    }
}

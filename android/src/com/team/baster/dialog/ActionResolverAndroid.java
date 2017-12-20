package com.team.baster.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.badlogic.gdx.utils.Array;
import com.team.baster.R;
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

    public ActionResolverAndroid(Context context) {
        handler = new Handler();
        this.context = context;
    }

    public void showDialogWithBestScore() {

//        context.startActivity(new Intent(context, MyListActivity.class));

        handler.post(() -> {
            Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            dialog.setContentView(R.layout.dialog_layout);

            List<Long> lastBest = scoreService.readLastBestScore();
            ListView lv = dialog.findViewById(R.id.lv);

            MyAdapter myAdapter = new MyAdapter(context, lastBest);
            lv.setAdapter(myAdapter);

            dialog.show();
        });
    }

    public void showToast(final CharSequence text) {
        handler.post(() -> Toast.makeText(context, text, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void showDialog() {
        handler.post(() -> {
            Dialog dialog = new Dialog(context);
            dialog.setTitle("Available soon");
            dialog.show();
        });
    }

}

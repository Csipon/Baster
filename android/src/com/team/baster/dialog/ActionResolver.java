package com.team.baster.dialog;

import com.badlogic.gdx.utils.Array;

import java.util.List;

/**
 * Created by Smeet on 31.10.2017.
 */

public interface ActionResolver {
    public void showToast(CharSequence text);
    public void showDialog();
    public void showDialogWithBestScore();
}

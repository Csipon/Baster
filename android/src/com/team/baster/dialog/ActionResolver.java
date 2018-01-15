package com.team.baster.dialog;

import com.team.baster.screens.MenuScreen;

/**
 * Created by Smeet on 31.10.2017.
 */

public interface ActionResolver {
    void showToast(CharSequence text);
    void showDialog();
    void showDialogWithBestScore();
    void showDialogLogin(MenuScreen menuScreen);
    void showDialogSetting(MenuScreen menuScreen);
}

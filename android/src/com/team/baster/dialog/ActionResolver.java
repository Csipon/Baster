package com.team.baster.dialog;

import java.util.List;

/**
 * Created by Smeet on 31.10.2017.
 */

public interface ActionResolver {
    void showToast(CharSequence text);
    void showDialog();
    void showDialogWithBestScore();
    List<String> showDialogLogin();
}

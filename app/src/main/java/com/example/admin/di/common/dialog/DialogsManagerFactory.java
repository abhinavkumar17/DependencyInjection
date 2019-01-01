package com.example.admin.di.common.dialog;

import android.support.v4.app.FragmentManager;

public class DialogsManagerFactory {

    public DialogsManager newDialogsManager(FragmentManager fragmentManager) {
        return new DialogsManager(fragmentManager);
    }
}

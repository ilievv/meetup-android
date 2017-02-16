package com.telerikacademy.meetup.ui.components.dialog.base;

import android.app.Activity;

public interface IDialogFactory {

    void initialize(Activity activity);

    Dialog createDialog();
}

package com.telerikacademy.meetup.ui.components.dialog;

import android.app.Activity;
import com.telerikacademy.meetup.ui.components.dialog.base.Dialog;
import com.telerikacademy.meetup.ui.components.dialog.base.IDialogFactory;

public final class DialogFactory implements IDialogFactory {

    private Activity activity;

    @Override
    public void initialize(Activity activity) {
        this.activity = activity;
    }

    @Override
    public Dialog createDialog() {
        throwIfUninitialized();
        return new MaterialDialog(activity);
    }

    private void throwIfUninitialized() {
        if (activity == null) {
            throw new RuntimeException("IDialogFactory is not initialized.");
        }
    }
}

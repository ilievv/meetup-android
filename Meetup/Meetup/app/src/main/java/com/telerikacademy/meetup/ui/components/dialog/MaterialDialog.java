package com.telerikacademy.meetup.ui.components.dialog;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import com.afollestad.materialdialogs.DialogAction;
import com.telerikacademy.meetup.ui.components.dialog.base.Dialog;

public class MaterialDialog extends Dialog {

    private com.afollestad.materialdialogs.MaterialDialog.Builder dialogBuilder;

    @Override
    public Dialog initialize(@NonNull Activity activity) {
        dialogBuilder = new com.afollestad.materialdialogs.MaterialDialog.Builder(activity);
        return this;
    }

    @Override
    public Dialog withTitle(@NonNull CharSequence title) {
        throwIfUninitialized();
        dialogBuilder.title(title);
        return this;
    }

    @Override
    public Dialog withTitle(@StringRes int title) {
        throwIfUninitialized();
        dialogBuilder.title(title);
        return this;
    }

    @Override
    public Dialog withContent(@NonNull CharSequence content) {
        throwIfUninitialized();
        dialogBuilder.content(content);
        return this;
    }

    @Override
    public Dialog withContent(@StringRes int content) {
        throwIfUninitialized();
        dialogBuilder.content(content);
        return this;
    }

    @Override
    public Dialog withIcon(@NonNull Drawable icon) {
        throwIfUninitialized();
        dialogBuilder.icon(icon);
        return this;
    }

    @Override
    public Dialog withIcon(@DrawableRes int icon) {
        throwIfUninitialized();
        dialogBuilder.iconRes(icon);
        return this;
    }

    @Override
    public Dialog withPositiveButton(@StringRes int text,
                                     final OnOptionButtonClick onPositiveListener) {

        throwIfUninitialized();
        dialogBuilder.positiveText(text);
        if (onPositiveListener != null) {
            dialogBuilder.onPositive(new com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull com.afollestad.materialdialogs.MaterialDialog dialog, @NonNull DialogAction which) {
                    onPositiveListener.onClick();
                }
            });
        }

        return this;
    }

    @Override
    public Dialog withPositiveButton(@NonNull CharSequence text,
                                     final OnOptionButtonClick onPositiveListener) {

        throwIfUninitialized();
        dialogBuilder.positiveText(text);
        if (onPositiveListener != null) {
            dialogBuilder.onPositive(new com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull com.afollestad.materialdialogs.MaterialDialog dialog, @NonNull DialogAction which) {
                    onPositiveListener.onClick();
                }
            });
        }

        return this;
    }

    @Override
    public Dialog withNeutralButton(@StringRes int text,
                                    final OnOptionButtonClick onNeutralListener) {

        throwIfUninitialized();
        dialogBuilder.neutralText(text);
        if (onNeutralListener != null) {
            dialogBuilder.onNeutral(new com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull com.afollestad.materialdialogs.MaterialDialog dialog, @NonNull DialogAction which) {
                    onNeutralListener.onClick();
                }
            });
        }

        return this;
    }

    @Override
    public Dialog withNeutralButton(@NonNull CharSequence text,
                                    final OnOptionButtonClick onNeutralListener) {

        throwIfUninitialized();
        dialogBuilder.neutralText(text);
        if (onNeutralListener != null) {
            dialogBuilder.onNeutral(new com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull com.afollestad.materialdialogs.MaterialDialog dialog, @NonNull DialogAction which) {
                    onNeutralListener.onClick();
                }
            });
        }

        return this;
    }

    @Override
    public Dialog withNegativeButton(@StringRes int text,
                                     final OnOptionButtonClick onNegativeListener) {

        throwIfUninitialized();
        dialogBuilder.negativeText(text);
        if (onNegativeListener != null) {
            dialogBuilder.onNegative(new com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull com.afollestad.materialdialogs.MaterialDialog dialog, @NonNull DialogAction which) {
                    onNegativeListener.onClick();
                }
            });
        }

        return this;
    }

    @Override
    public Dialog withNegativeButton(@NonNull CharSequence text,
                                     final OnOptionButtonClick onNegativeListener) {

        throwIfUninitialized();
        dialogBuilder.negativeText(text);
        if (onNegativeListener != null) {
            dialogBuilder.onNegative(new com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull com.afollestad.materialdialogs.MaterialDialog dialog, @NonNull DialogAction which) {
                    onNegativeListener.onClick();
                }
            });
        }

        return this;
    }

    @Override
    public void show() {
        throwIfUninitialized();
        dialogBuilder.show();
    }

    private void throwIfUninitialized() {
        if (dialogBuilder == null) {
            throw new RuntimeException("Dialog is not initialized.");
        }
    }
}

package com.telerikacademy.meetup.ui.components.dialog;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import com.afollestad.materialdialogs.DialogAction;
import com.telerikacademy.meetup.ui.components.dialog.base.Dialog;

public class MaterialDialog extends Dialog {

    private final com.afollestad.materialdialogs.MaterialDialog.Builder dialogBuilder;

    public MaterialDialog(@NonNull Activity activity) {
        dialogBuilder = new com.afollestad.materialdialogs.MaterialDialog.Builder(activity);
    }

    @Override
    public Dialog withTitle(@NonNull CharSequence title) {
        dialogBuilder.title(title);
        return this;
    }

    @Override
    public Dialog withTitle(@StringRes int title) {
        dialogBuilder.title(title);
        return this;
    }

    @Override
    public Dialog withContent(@NonNull CharSequence content) {
        dialogBuilder.content(content);
        return this;
    }

    @Override
    public Dialog withContent(@StringRes int content) {
        dialogBuilder.content(content);
        return this;
    }

    @Override
    public Dialog withIcon(@NonNull Drawable icon) {
        dialogBuilder.icon(icon);
        return this;
    }

    @Override
    public Dialog withIcon(@DrawableRes int icon) {
        dialogBuilder.iconRes(icon);
        return this;
    }

    @Override
    public Dialog withPositiveButton(@StringRes int text,
                                     final OnOptionButtonClick onPositiveListener) {

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
        dialogBuilder.show();
    }
}

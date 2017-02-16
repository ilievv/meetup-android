package com.telerikacademy.meetup.ui.components.dialog.base;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

public abstract class Dialog {

    public abstract Dialog withTitle(@NonNull CharSequence title);

    public abstract Dialog withTitle(@StringRes int title);

    public abstract Dialog withContent(@NonNull CharSequence content);

    public abstract Dialog withContent(@StringRes int content);

    public abstract Dialog withIcon(@NonNull Drawable icon);

    public abstract Dialog withIcon(@DrawableRes int icon);

    public abstract Dialog withPositiveButton(@StringRes int text, OnOptionButtonClick onPositiveListener);

    public abstract Dialog withPositiveButton(@NonNull CharSequence text, OnOptionButtonClick onPositiveListener);

    public abstract Dialog withNeutralButton(@StringRes int text, OnOptionButtonClick onNeutralListener);

    public abstract Dialog withNeutralButton(@NonNull CharSequence text, OnOptionButtonClick onNeutralListener);

    public abstract Dialog withNegativeButton(@StringRes int text, OnOptionButtonClick onNegativeListener);

    public abstract Dialog withNegativeButton(@NonNull CharSequence text, OnOptionButtonClick onNegativeListener);

    public abstract void show();

    public interface OnOptionButtonClick {

        void onClick();
    }
}

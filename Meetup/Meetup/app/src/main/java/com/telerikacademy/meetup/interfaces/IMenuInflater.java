package com.telerikacademy.meetup.interfaces;

import android.support.annotation.MenuRes;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public interface IMenuInflater {

    void inflateMenu(@MenuRes int menuRes, Menu menu, MenuInflater menuInflater);

    void setNavigationOnClickListener();

    void setNavigationOnClickListener(View.OnClickListener onClickListener);
}

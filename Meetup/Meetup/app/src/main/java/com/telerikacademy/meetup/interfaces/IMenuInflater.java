package com.telerikacademy.meetup.interfaces;

import android.support.annotation.MenuRes;
import android.view.Menu;
import android.view.MenuInflater;

public interface IMenuInflater {

    void inflateMenu(@MenuRes int menuRes, Menu menu, MenuInflater menuInflater);
}

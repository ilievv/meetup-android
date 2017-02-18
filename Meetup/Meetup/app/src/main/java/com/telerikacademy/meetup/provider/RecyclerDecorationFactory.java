package com.telerikacademy.meetup.provider;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import com.telerikacademy.meetup.config.di.qualifier.ApplicationContext;
import com.telerikacademy.meetup.provider.base.IRecyclerDecorationFactory;

import javax.inject.Inject;

public class RecyclerDecorationFactory implements IRecyclerDecorationFactory {

    private final Context context;

    @Inject
    public RecyclerDecorationFactory(@ApplicationContext Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ItemDecoration createDividerDecoration(int orientation) {
        return new DividerItemDecoration(context, orientation);
    }
}

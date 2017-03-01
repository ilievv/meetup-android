package com.telerikacademy.meetup.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.config.base.IApiConstants;
import com.telerikacademy.meetup.config.di.annotation.HorizontalLayoutManager;
import com.telerikacademy.meetup.config.di.module.ControllerModule;
import com.telerikacademy.meetup.model.base.IVenue;
import com.telerikacademy.meetup.network.local.base.ILocalData;
import com.telerikacademy.meetup.ui.adapter.RecentVenuesAdapter;
import com.telerikacademy.meetup.ui.fragment.base.IRecentVenues;
import com.telerikacademy.meetup.util.base.IUserSession;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class RecentVenuesFragment extends Fragment
        implements IRecentVenues {

    @Inject
    @HorizontalLayoutManager
    LinearLayoutManager layoutManager;
    @Inject
    IUserSession userSession;
    @Inject
    IApiConstants constants;
    @Inject
    ILocalData localData;

    @BindView(R.id.rv_recent_venues)
    RecyclerView recentVenuesRv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recent_venues, container, false);
        BaseApplication.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        injectDependencies();
        RecentVenuesAdapter adapter = new RecentVenuesAdapter(userSession, new ArrayList<IVenue>());
        recentVenuesRv.setLayoutManager(layoutManager);
        recentVenuesRv.setAdapter(adapter);
    }

    @Override
    public void showRecentVenues() {
        localData
                .getRecentVenues()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<IVenue>>() {
                    @Override
                    public void accept(List<IVenue> recentVenues) throws Exception {
                        recentVenuesRv.setAdapter(new RecentVenuesAdapter(userSession, recentVenues));
                    }
                });
    }

    private void injectDependencies() {
        BaseApplication
                .from(getContext())
                .getComponent()
                .getControllerComponent(new ControllerModule(
                        getActivity(), getFragmentManager()
                ))
                .inject(this);
    }
}

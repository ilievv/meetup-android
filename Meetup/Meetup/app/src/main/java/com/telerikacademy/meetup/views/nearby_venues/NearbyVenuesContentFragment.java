package com.telerikacademy.meetup.views.nearby_venues;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.providers.base.IRecyclerDecorationFactory;

import javax.inject.Inject;

public class NearbyVenuesContentFragment extends Fragment {

    @Inject
    IRecyclerDecorationFactory decorationFactory;

    @BindView(R.id.rv_venues)
    RecyclerView recyclerView;

    public NearbyVenuesContentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_nearby_venues_content, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        injectDependencies();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.addItemDecoration(decorationFactory.createDividerDecoration(
                linearLayoutManager.getOrientation()
        ));
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    private void injectDependencies() {
        ((BaseApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);
    }
}

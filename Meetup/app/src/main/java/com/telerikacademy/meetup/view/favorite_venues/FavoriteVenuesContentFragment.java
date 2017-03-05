package com.telerikacademy.meetup.view.favorite_venues;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.config.di.annotation.VerticalLayoutManager;
import com.telerikacademy.meetup.config.di.module.ControllerModule;
import com.telerikacademy.meetup.model.base.IVenueShort;
import com.telerikacademy.meetup.provider.base.IDecorationFactory;
import com.telerikacademy.meetup.ui.component.dialog.base.IDialog;
import com.telerikacademy.meetup.ui.component.dialog.base.IDialogFactory;
import com.telerikacademy.meetup.view.favorite_venues.base.IFavoriteVenuesContract;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class FavoriteVenuesContentFragment extends Fragment
        implements IFavoriteVenuesContract.View,
        SwipeRefreshLayout.OnRefreshListener {

    @Inject
    @VerticalLayoutManager
    LinearLayoutManager layoutManager;
    @Inject
    IDialogFactory dialogFactory;
    @Inject
    IDecorationFactory decorationFactory;

    @BindView(R.id.srl_favorite_venues_content)
    SwipeRefreshLayout favoriteItemsRefreshLayout;
    @BindView(R.id.rv_favorite_venues_content)
    RecyclerView favoriteItemsRecyclerView;
    @BindView(R.id.tv_no_favorite_venues)
    TextView noFavoriteVenuesTextView;

    private IFavoriteVenuesContract.Presenter presenter;
    private FavoriteVenuesAdapter favoriteItemsAdapter;
    private IDialog progressDialog;

    public FavoriteVenuesContentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite_venues_content, container, false);
        BaseApplication.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        injectDependencies();
        initialize();
        presenter.loadData(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadData(false);
    }

    @Override
    public void setPresenter(IFavoriteVenuesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setVenues(List<? extends IVenueShort> venues) {
        favoriteItemsAdapter.swap(venues);

        if (venues == null || venues.isEmpty()) {
            noFavoriteVenuesTextView.setVisibility(View.VISIBLE);
            favoriteItemsRefreshLayout.setVisibility(View.GONE);
        } else {
            noFavoriteVenuesTextView.setVisibility(View.GONE);
            favoriteItemsRefreshLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void startLoading() {
        progressDialog.show();
    }

    @Override
    public void stopLoading() {
        progressDialog.hide();
        if (favoriteItemsRefreshLayout.isRefreshing()) {
            favoriteItemsRefreshLayout.setRefreshing(false);
        }
    }

    private void initialize() {
        progressDialog = dialogFactory
                .createDialog()
                .withContent(R.string.dialog_loading_content)
                .withProgress();

        favoriteItemsRefreshLayout.setOnRefreshListener(this);
        favoriteItemsRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        DividerItemDecoration dividerDecoration = decorationFactory
                .createDividerDecoration(layoutManager.getOrientation(), R.drawable.horizontal_divider);
        favoriteItemsAdapter = new FavoriteVenuesAdapter(new ArrayList<IVenueShort>());
        favoriteItemsRecyclerView.addItemDecoration(dividerDecoration);
        favoriteItemsRecyclerView.setLayoutManager(layoutManager);
        favoriteItemsRecyclerView.setAdapter(favoriteItemsAdapter);
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

    @Override
    public void onRefresh() {
        presenter.loadData(false);
    }
}

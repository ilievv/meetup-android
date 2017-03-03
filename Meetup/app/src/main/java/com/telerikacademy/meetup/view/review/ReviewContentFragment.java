package com.telerikacademy.meetup.view.review;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.config.di.module.ControllerModule;
import com.telerikacademy.meetup.view.review.base.IReviewContract;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;

public class ReviewContentFragment extends Fragment implements IReviewContract.View {

    private IReviewContract.Presenter presenter;

    @BindView(R.id.review_venue_name_holder)
    TextView venueNameHolder;
    @BindView(R.id.review_comment_holder)
    EditText commentHolder;

    public ReviewContentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_review_content, container, false);
        BaseApplication.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        injectDependencies();
    }

    @Override
    public void postComment() {

    }

    @Override
    public void setPresenter(IReviewContract.Presenter presenter) {
        this.presenter = presenter;
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

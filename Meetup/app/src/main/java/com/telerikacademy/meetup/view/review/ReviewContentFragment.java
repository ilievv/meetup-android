package com.telerikacademy.meetup.view.review;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.config.di.module.ControllerModule;
import com.telerikacademy.meetup.model.VenueDetail;
import com.telerikacademy.meetup.provider.base.IIntentFactory;
import com.telerikacademy.meetup.ui.component.dialog.base.IDialog;
import com.telerikacademy.meetup.ui.component.dialog.base.IDialogFactory;
import com.telerikacademy.meetup.view.home.HomeActivity;
import com.telerikacademy.meetup.view.review.base.IReviewContract;
import com.telerikacademy.meetup.view.venue_details.VenueDetailsActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ReviewContentFragment extends Fragment implements IReviewContract.View {
    private static final String EXTRA_CURRENT_VENUE_ID =
            VenueDetailsActivity.class.getCanonicalName() + ".CURRENT_VENUE_ID";

    @Inject
    IIntentFactory intentFactory;
    @Inject
    IDialogFactory dialogFactory;

    private IReviewContract.Presenter presenter;
    private IDialog progressDialog;

    @BindView(R.id.review_venue_name_holder)
    TextView venueNameHolder;

    @BindView(R.id.review_comment_holder)
    EditText commentHolder;

    @BindView(R.id.review_comment_label)
    TextView commentLabel;

    @BindView(R.id.review_post_comment_btn)
    Button postCommentButton;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        injectDependencies();

        progressDialog = dialogFactory
                .createDialog()
                .withContent(R.string.dialog_loading_content)
                .withProgress();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        injectDependencies();
    }

    @Override
    public void notifySuccessful(String successMsg) {
        Toast.makeText(getContext(), successMsg, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void notifyError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void redirectToVenueDetails(String venueId) {
        Intent venueDetailsIntent = intentFactory.createIntentToFront(VenueDetailsActivity.class);
        venueDetailsIntent.putExtra(EXTRA_CURRENT_VENUE_ID, venueId);
        startActivity(venueDetailsIntent);
    }

    @Override
    public void startLoading() {
        progressDialog.show();
    }

    @Override
    public void stopLoading() {
        progressDialog.hide();
    }

    @Override
    public void setPresenter(IReviewContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @OnClick(R.id.review_post_comment_btn)
    void onPostCommentButtonClick() {
        String text = commentHolder.getText().toString();
        commentHolder.setText("");
        presenter.postComment(text);
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

package com.telerikacademy.meetup.view.review;

import com.telerikacademy.meetup.network.remote.base.IVenueData;
import com.telerikacademy.meetup.view.review.base.IReviewContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ReviewPresenter implements IReviewContract.Presenter {

    private final IVenueData venueData;
    private IReviewContract.View view;
    private String venueId;
    private String venueName;
    private String venueAddress;

    @Inject
    public ReviewPresenter(IVenueData venueData){
        this.venueData = venueData;
    }

    @Override
    public void postComment(String text) {
        venueData.commentVenue(this.venueId, this.venueName, this.venueAddress, text)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        view.startLoading();
                    }

                    @Override
                    public void onNext(String msg) {
                        //view.notifySuccessful(msg); - do not work ?
                        view.redirectToVenueDetails(venueId);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.notifyError(e.getMessage());
                        view.stopLoading();
                    }

                    @Override
                    public void onComplete() {
                        view.stopLoading();
                    }
                });
    }

    @Override
    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    @Override
    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    @Override
    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    @Override
    public void setView(IReviewContract.View view) {
        this.view = view;
    }
}

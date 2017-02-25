package com.telerikacademy.meetup.config.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import com.telerikacademy.meetup.config.di.annotation.ActivityContext;
import com.telerikacademy.meetup.config.di.annotation.ControllerScope;
import com.telerikacademy.meetup.data.local.base.ILocalData;
import com.telerikacademy.meetup.data.network.base.IUserData;
import com.telerikacademy.meetup.provider.GoogleVenuePhotoProvider;
import com.telerikacademy.meetup.provider.IntentFactory;
import com.telerikacademy.meetup.provider.base.IIntentFactory;
import com.telerikacademy.meetup.provider.base.ILocationProvider;
import com.telerikacademy.meetup.provider.base.IVenuePhotoProvider;
import com.telerikacademy.meetup.ui.components.dialog.DialogFactory;
import com.telerikacademy.meetup.ui.components.dialog.MaterialDialog;
import com.telerikacademy.meetup.ui.components.dialog.base.IDialog;
import com.telerikacademy.meetup.ui.components.dialog.base.IDialogFactory;
import com.telerikacademy.meetup.ui.components.navigation_drawer.MaterialDrawer;
import com.telerikacademy.meetup.ui.components.navigation_drawer.base.IDrawer;
import com.telerikacademy.meetup.util.PermissionHandler;
import com.telerikacademy.meetup.util.base.IPermissionHandler;
import com.telerikacademy.meetup.util.base.IValidator;
import com.telerikacademy.meetup.view.home.HomeContentPresenter;
import com.telerikacademy.meetup.view.home.HomeHeaderPresenter;
import com.telerikacademy.meetup.view.home.base.IHomeContentContract;
import com.telerikacademy.meetup.view.home.base.IHomeHeaderContract;
import com.telerikacademy.meetup.view.nearby_venues.NearbyVenuesPresenter;
import com.telerikacademy.meetup.view.nearby_venues.base.INearbyVenuesContract;
import com.telerikacademy.meetup.view.sign_in.SignInPresenter;
import com.telerikacademy.meetup.view.sign_in.base.ISignInContract;
import com.telerikacademy.meetup.view.sign_up.SignUpPresenter;
import com.telerikacademy.meetup.view.sign_up.base.ISignUpContract;
import com.telerikacademy.meetup.view.venue_details.VenueDetailsPresenter;
import com.telerikacademy.meetup.view.venue_details.base.IVenueDetailsContract;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;

@Module
public class ControllerModule {

    private final Activity activity;
    private final FragmentManager fragmentManager;

    public ControllerModule(Activity activity, FragmentManager fragmentManager) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    @Provides
    @ControllerScope
    Activity provideActivity() {
        return activity;
    }

    @Provides
    @ActivityContext
    @ControllerScope
    Context provideContext() {
        return activity;
    }

    @Provides
    @ControllerScope
    FragmentManager provideFragmentManager() {
        return fragmentManager;
    }

    @Inject
    @Provides
    @ControllerScope
    LinearLayoutManager provideLinearLayoutManager(Activity activity) {
        return new LinearLayoutManager(activity);
    }

    @Inject
    @Provides
    @ControllerScope
    IDrawer provideNavigationDrawer(Activity activity) {
        return new MaterialDrawer(activity);
    }

    @Inject
    @Provides
    @ControllerScope
    IDialog provideDialog(Activity activity) {
        return new MaterialDialog(activity);
    }

    @Provides
    @ControllerScope
    IHomeContentContract.Presenter provideHomeContentPresenter() {
        return new HomeContentPresenter();
    }

    @Inject
    @Provides
    @ControllerScope
    IHomeHeaderContract.Presenter provideHomeHeaderPresenter(ILocationProvider locationProvider) {
        return new HomeHeaderPresenter(locationProvider);
    }

    @Provides
    @ControllerScope
    INearbyVenuesContract.Presenter provideNearbyVenuesPresenter() {
        return new NearbyVenuesPresenter();
    }

    @Inject
    @Provides
    @ControllerScope
    ISignInContract.Presenter provideSignInPresenter(IUserData userdata, IValidator validator) {
        return new SignInPresenter(userdata, validator);
    }

    @Inject
    @Provides
    @ControllerScope
    ISignUpContract.Presenter provideSignUpPresenter(IUserData userData, IValidator validator) {
        return new SignUpPresenter(userData, validator);
    }

    @Inject
    @Provides
    @ControllerScope
    IVenueDetailsContract.Presenter provideVenueDetailsPresenter(IVenuePhotoProvider venuePhotoProvider, ILocalData localData) {
        return new VenueDetailsPresenter(venuePhotoProvider, localData);
    }

    @Inject
    @Provides
    @ControllerScope
    IIntentFactory provideIntentFactory(@ActivityContext Context context) {
        return new IntentFactory(context);
    }

    @Inject
    @Provides
    @ControllerScope
    IPermissionHandler providePermissionHandler(Activity activity) {
        return new PermissionHandler(activity);
    }

    @Inject
    @Provides
    @ControllerScope
    IDialogFactory provideDialogFactory(Activity activity) {
        return new DialogFactory(activity);
    }

    @Inject
    @Provides
    @ControllerScope
    IVenuePhotoProvider provideVenuePhotoProvider(Activity activity) {
        return new GoogleVenuePhotoProvider(activity);
    }
}

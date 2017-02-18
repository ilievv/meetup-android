package com.telerikacademy.meetup.config.di.component;

import com.telerikacademy.meetup.config.di.module.*;
import com.telerikacademy.meetup.provider.GoogleLocationProvider;
import com.telerikacademy.meetup.ui.components.navigation_drawer.MaterialDrawerItemFactory;
import com.telerikacademy.meetup.ui.fragments.ToolbarFragment;
import com.telerikacademy.meetup.util.UserSession;
import com.telerikacademy.meetup.view.home.HomeActivity;
import com.telerikacademy.meetup.view.home.HomeContentFragment;
import com.telerikacademy.meetup.view.home.HomeHeaderFragment;
import com.telerikacademy.meetup.view.nearby_venues.NearbyVenuesContentFragment;
import com.telerikacademy.meetup.view.sign_in.SignInActivity;
import com.telerikacademy.meetup.view.sign_up.SignUpActivity;
import dagger.Component;
import okhttp3.OkHttpClient;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        PresenterModule.class,
        ProviderModule.class,
        NetworkModule.class,
        UtilModule.class,
        UiModule.class
})
public interface ApplicationComponent {

    void inject(HomeActivity homeActivity);

    void inject(HomeContentFragment homeContentFragment);

    void inject(HomeHeaderFragment homeToolbarFragment);

    void inject(NearbyVenuesContentFragment nearbyVenuesContentFragment);

    void inject(SignInActivity signInActivity);

    void inject(SignUpActivity signUpActivity);

    void inject(GoogleLocationProvider locationProvider);

    void inject(OkHttpClient okHttpClient);

    void inject(UserSession userSession);

    void inject(ToolbarFragment toolbarFragment);

    void inject(MaterialDrawerItemFactory materialDrawerItemFactory);
}

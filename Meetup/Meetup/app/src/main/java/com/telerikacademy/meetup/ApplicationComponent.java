package com.telerikacademy.meetup;

import com.telerikacademy.meetup.views.home.HomeActivity;
import com.telerikacademy.meetup.views.sign_in.SignInActivity;
import com.telerikacademy.meetup.views.sign_up.SignUpActivity;
import com.telerikacademy.meetup.config.modules.AndroidModule;
import com.telerikacademy.meetup.config.modules.NetworkModule;
import com.telerikacademy.meetup.config.modules.ProviderModule;
import com.telerikacademy.meetup.config.modules.UtilModule;
import com.telerikacademy.meetup.ui.fragments.ToolbarFragment;
import com.telerikacademy.meetup.providers.GoogleLocationProvider;
import com.telerikacademy.meetup.utils.UserSession;
import dagger.Component;
import okhttp3.OkHttpClient;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        AndroidModule.class,
        UtilModule.class,
        ProviderModule.class,
        NetworkModule.class})
public interface ApplicationComponent {

    void inject(HomeActivity homeActivity);

    void inject(SignInActivity signInActivity);

    void inject(SignUpActivity signUpActivity);

    void inject(GoogleLocationProvider locationProvider);

    void inject(OkHttpClient okHttpClient);

    void inject(UserSession userSession);

    void inject(ToolbarFragment toolbarFragment);
}

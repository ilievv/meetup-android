package com.telerikacademy.meetup;

import com.telerikacademy.meetup.config.modules.*;
import com.telerikacademy.meetup.services.GoogleLocationProvider;
import com.telerikacademy.meetup.ui.components.dialog.MaterialDialog;
import com.telerikacademy.meetup.ui.components.navigation_drawer.MaterialDrawerItemFactory;
import com.telerikacademy.meetup.ui.fragments.ToolbarFragment;
import com.telerikacademy.meetup.utils.UserSession;
import com.telerikacademy.meetup.views.home.HomeActivity;
import com.telerikacademy.meetup.views.sign_in.SignInActivity;
import com.telerikacademy.meetup.views.sign_up.SignUpActivity;
import dagger.Component;
import okhttp3.OkHttpClient;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        AndroidModule.class,
        UtilModule.class,
        ServiceModule.class,
        NetworkModule.class,
        UIModule.class
})
public interface ApplicationComponent {

    void inject(HomeActivity homeActivity);

    void inject(SignInActivity signInActivity);

    void inject(SignUpActivity signUpActivity);

    void inject(GoogleLocationProvider locationProvider);

    void inject(OkHttpClient okHttpClient);

    void inject(UserSession userSession);

    void inject(ToolbarFragment toolbarFragment);

    void inject(MaterialDrawerItemFactory materialDrawerItemFactory);
}

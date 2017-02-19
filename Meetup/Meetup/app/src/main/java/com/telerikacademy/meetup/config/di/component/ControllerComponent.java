package com.telerikacademy.meetup.config.di.component;

import com.telerikacademy.meetup.config.di.module.ControllerModule;
import com.telerikacademy.meetup.config.di.annotation.ControllerScope;
import com.telerikacademy.meetup.ui.fragments.ToolbarFragment;
import com.telerikacademy.meetup.view.home.HomeActivity;
import com.telerikacademy.meetup.view.home.HomeHeaderFragment;
import com.telerikacademy.meetup.view.nearby_venues.NearbyVenuesContentFragment;
import com.telerikacademy.meetup.view.sign_in.SignInActivity;
import com.telerikacademy.meetup.view.sign_up.SignUpActivity;
import dagger.Subcomponent;

@ControllerScope
@Subcomponent(modules = {ControllerModule.class})
public interface ControllerComponent {

    void inject(HomeActivity homeActivity);

    void inject(HomeHeaderFragment homeHeaderFragment);

    void inject(SignInActivity signInActivity);

    void inject(SignUpActivity signUpActivity);

    void inject(NearbyVenuesContentFragment nearbyVenuesContentFragment);

    void inject(ToolbarFragment toolbarFragment);
}

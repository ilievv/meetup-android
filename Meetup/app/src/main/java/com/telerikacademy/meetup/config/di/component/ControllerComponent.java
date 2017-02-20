package com.telerikacademy.meetup.config.di.component;

import com.telerikacademy.meetup.config.di.annotation.ControllerScope;
import com.telerikacademy.meetup.config.di.module.ControllerModule;
import com.telerikacademy.meetup.ui.fragments.ToolbarFragment;
import com.telerikacademy.meetup.view.home.HomeActivity;
import com.telerikacademy.meetup.view.home.HomeContentFragment;
import com.telerikacademy.meetup.view.home.HomeHeaderFragment;
import com.telerikacademy.meetup.view.nearby_venues.NearbyVenuesActivity;
import com.telerikacademy.meetup.view.nearby_venues.NearbyVenuesContentFragment;
import com.telerikacademy.meetup.view.sign_in.SignInActivity;
import com.telerikacademy.meetup.view.sign_in.SignInContentFragment;
import com.telerikacademy.meetup.view.sign_up.SignUpActivity;
import com.telerikacademy.meetup.view.sign_up.SignUpContentFragment;
import com.telerikacademy.meetup.view.venue_details.VenueDetailsActivity;
import dagger.Subcomponent;

@ControllerScope
@Subcomponent(modules = {ControllerModule.class})
public interface ControllerComponent {

    void inject(HomeActivity homeActivity);

    void inject(HomeHeaderFragment homeHeaderFragment);

    void inject(HomeContentFragment homeContentFragment);

    void inject(NearbyVenuesActivity nearbyVenuesActivity);

    void inject(NearbyVenuesContentFragment nearbyVenuesContentFragment);

    void inject(VenueDetailsActivity venueDetailsActivity);

    void inject(SignInActivity signInActivity);

    void inject(SignInContentFragment signInContentFragment);

    void inject(SignUpActivity signUpActivity);

    void inject(SignUpContentFragment signUpContentFragment);

    void inject(ToolbarFragment toolbarFragment);
}

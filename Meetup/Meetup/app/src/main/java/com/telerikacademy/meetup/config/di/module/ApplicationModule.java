package com.telerikacademy.meetup.config.di.module;

import android.app.Application;
import android.content.Context;
import com.telerikacademy.meetup.config.di.annotation.ApplicationContext;
import com.telerikacademy.meetup.config.di.annotation.ApplicationScope;
import com.telerikacademy.meetup.provider.GoogleLocationProvider;
import com.telerikacademy.meetup.provider.HttpResponseFactory;
import com.telerikacademy.meetup.provider.LocationFactory;
import com.telerikacademy.meetup.provider.RecyclerDecorationFactory;
import com.telerikacademy.meetup.provider.base.IHttpResponseFactory;
import com.telerikacademy.meetup.provider.base.ILocationFactory;
import com.telerikacademy.meetup.provider.base.IRecyclerDecorationFactory;
import com.telerikacademy.meetup.provider.base.LocationProvider;
import com.telerikacademy.meetup.ui.components.dialog.DialogFactory;
import com.telerikacademy.meetup.ui.components.dialog.base.IDialogFactory;
import com.telerikacademy.meetup.ui.components.navigation_drawer.MaterialDrawerItemFactory;
import com.telerikacademy.meetup.ui.components.navigation_drawer.base.IDrawerItemFactory;
import com.telerikacademy.meetup.util.*;
import com.telerikacademy.meetup.util.base.*;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationScope
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Inject
    @Provides
    @ApplicationScope
    LocationProvider provideLocationProvider(@ApplicationContext Context context, ILocationFactory locationFactory) {
        return new GoogleLocationProvider(context, locationFactory);
    }

    @Provides
    @ApplicationScope
    IPermissionHandler providePermissionHandler() {
        return new PermissionHandler();
    }

    @Inject
    @Provides
    @ApplicationScope
    IHttpRequester provideHttpRequester(IHttpResponseFactory responseFactory) {
        return new OkHttpRequester(responseFactory);
    }

    @Provides
    @ApplicationScope
    IHttpResponseFactory provideHttpResponseFactory() {
        return new HttpResponseFactory();
    }

    @Provides
    @ApplicationScope
    IJsonParser provideJsonParser() {
        return new GsonParser();
    }

    @Provides
    @ApplicationScope
    IHashProvider provideHashProvider() {
        return new SHA1HashProvider();
    }

    @Inject
    @Provides
    @ApplicationScope
    IUserSession provideUserSession(@ApplicationContext Context context) {
        return new UserSession(context);
    }

    @Provides
    @ApplicationScope
    IValidator provideValidator() {
        return new Validator();
    }

    @Provides
    @ApplicationScope
    IRecyclerDecorationFactory provideRecyclerDecorationFactory(@ApplicationContext Context context) {
        return new RecyclerDecorationFactory(context);
    }

    @Provides
    @ApplicationScope
    ILocationFactory provideLocationFactory() {
        return new LocationFactory();
    }

    @Provides
    @ApplicationScope
    IDialogFactory provideDialogFactory() {
        return new DialogFactory();
    }

    @Inject
    @Provides
    @ApplicationScope
    IDrawerItemFactory provideDrawerItemFactory(@ApplicationContext Context context) {
        return new MaterialDrawerItemFactory(context);
    }
}

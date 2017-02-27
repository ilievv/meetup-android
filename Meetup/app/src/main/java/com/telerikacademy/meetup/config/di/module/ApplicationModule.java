package com.telerikacademy.meetup.config.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.telerikacademy.meetup.config.ApiDevelopmentConstants;
import com.telerikacademy.meetup.config.GoogleApiDevelopmentConstants;
import com.telerikacademy.meetup.config.base.IApiConstants;
import com.telerikacademy.meetup.config.base.IGoogleApiConstants;
import com.telerikacademy.meetup.config.di.annotation.ApplicationContext;
import com.telerikacademy.meetup.config.di.annotation.ApplicationScope;
import com.telerikacademy.meetup.config.di.annotation.UserModel;
import com.telerikacademy.meetup.data.local.base.ILocalData;
import com.telerikacademy.meetup.data.local.realm.RealmLocalData;
import com.telerikacademy.meetup.model.gson.User;
import com.telerikacademy.meetup.data.network.UserData;
import com.telerikacademy.meetup.data.network.VenueData;
import com.telerikacademy.meetup.data.network.base.IUserData;
import com.telerikacademy.meetup.data.network.base.IVenueData;
import com.telerikacademy.meetup.provider.*;
import com.telerikacademy.meetup.provider.base.*;
import com.telerikacademy.meetup.ui.component.navigation_drawer.MaterialDrawerItemFactory;
import com.telerikacademy.meetup.ui.component.navigation_drawer.base.IDrawerItemFactory;
import com.telerikacademy.meetup.util.*;
import com.telerikacademy.meetup.util.base.*;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import java.lang.reflect.Type;

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
    ILocationProvider provideLocationProvider(@ApplicationContext Context context, ILocationFactory locationFactory) {
        return new GoogleLocationProvider(context, locationFactory);
    }

    @Inject
    @Provides
    @ApplicationScope
    SharedPreferences provideSharedPreferences(@ApplicationContext Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @ApplicationScope
    @UserModel
    Type provideUserModelType() {
        return User.class;
    }

    @Provides
    @ApplicationScope
    IApiConstants provideApiConstants() {
        return new ApiDevelopmentConstants();
    }

    @Provides
    @ApplicationScope
    IGoogleApiConstants provideGoogleApiConstants() {
        return new GoogleApiDevelopmentConstants();
    }

    @Inject
    @Provides
    @ApplicationScope
    IUserData provideUserData(IApiConstants apiConstants, IHttpRequester httpRequester,
                              IJsonParser jsonParser, IUserSession userSession,
                              IHashProvider hashProvider, @UserModel Type userModelType) {

        return new UserData(apiConstants, httpRequester, jsonParser, userSession, hashProvider, userModelType);
    }

    @Inject
    @Provides
    @ApplicationScope
    IVenueData provideVenueData(IGoogleApiConstants googleApiConstants, IHttpRequester httpRequester,
                                IJsonParser jsonParser, IVenueFactory venueFactory) {

        return new VenueData(googleApiConstants, httpRequester, jsonParser, venueFactory);
    }

    @Inject
    @Provides
    @ApplicationScope
    ILocalData provideLocalData(@ApplicationContext Context context, IUserSession userSession, IImageUtil imageUtil, IApiConstants constants) {

        return new RealmLocalData(context, userSession, imageUtil, constants);
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
    IUserSession provideUserSession(SharedPreferences sharedPreferences) {
        return new UserSession(sharedPreferences);
    }

    @Inject
    @Provides
    @ApplicationScope
    IImageUtil provideImageUtil() {
        return new ImageUtil();
    }

    @Provides
    @ApplicationScope
    IValidator provideValidator() {
        return new Validator();
    }

    @Provides
    @ApplicationScope
    IVenueFactory provideVenueFactory() {
        return new VenueFactory();
    }

    @Provides
    @ApplicationScope
    ILocationFactory provideLocationFactory() {
        return new LocationFactory();
    }

    @Provides
    @ApplicationScope
    IRecyclerDecorationFactory provideRecyclerDecorationFactory(@ApplicationContext Context context) {
        return new RecyclerDecorationFactory(context);
    }

    @Inject
    @Provides
    @ApplicationScope
    IDrawerItemFactory provideDrawerItemFactory(@ApplicationContext Context context) {
        return new MaterialDrawerItemFactory(context);
    }
}

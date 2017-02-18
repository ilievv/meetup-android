package com.telerikacademy.meetup.config.di.modules;

import com.telerikacademy.meetup.utils.OkHttpRequester;
import com.telerikacademy.meetup.providers.ResponseFactory;
import com.telerikacademy.meetup.utils.base.IHttpRequester;
import com.telerikacademy.meetup.providers.base.IHttpResponseFactory;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Singleton;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    IHttpResponseFactory provideResponseFactory() {
        return new ResponseFactory();
    }

    @Inject
    @Provides
    @Singleton
    IHttpRequester provideHttpRequester(IHttpResponseFactory httpResponseFactory) {
        return new OkHttpRequester(httpResponseFactory);
    }
}

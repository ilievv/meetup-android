package com.telerikacademy.meetup.config.di.module;

import com.telerikacademy.meetup.util.OkHttpRequester;
import com.telerikacademy.meetup.provider.ResponseFactory;
import com.telerikacademy.meetup.util.base.IHttpRequester;
import com.telerikacademy.meetup.provider.base.IHttpResponseFactory;
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

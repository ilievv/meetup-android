package com.telerikacademy.meetup.config.modules;

import com.telerikacademy.meetup.network.OkHttpRequester;
import com.telerikacademy.meetup.network.ResponseFactory;
import com.telerikacademy.meetup.network.base.IHttpRequester;
import com.telerikacademy.meetup.network.base.IHttpResponseFactory;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Singleton;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public IHttpResponseFactory provideResponseFactory() {
        return new ResponseFactory();
    }

    @Inject
    @Provides
    @Singleton
    public IHttpRequester provideHttpRequester(IHttpResponseFactory httpResponseFactory) {
        return new OkHttpRequester(httpResponseFactory);
    }
}

package com.telerikacademy.meetup.config.modules;

import com.telerikacademy.meetup.utils.OkHttpRequester;
import com.telerikacademy.meetup.utils.ResponseFactory;
import com.telerikacademy.meetup.utils.base.IHttpRequester;
import com.telerikacademy.meetup.utils.base.IHttpResponseFactory;
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

package com.meetup.config.modules;

import com.meetup.utils.OkHttpRequester;
import com.meetup.utils.ResponseFactory;
import com.meetup.utils.base.IHttpRequester;
import com.meetup.utils.base.IHttpResponseFactory;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Singleton;

@Module
public class UtilModule {

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

package com.meetup.config.modules;

import com.meetup.utils.ResponseFactory;
import com.meetup.utils.base.IResponseFactory;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class UtilModule {

    @Provides
    @Singleton
    public IResponseFactory provideResponseFactory() {
        return new ResponseFactory();
    }
}

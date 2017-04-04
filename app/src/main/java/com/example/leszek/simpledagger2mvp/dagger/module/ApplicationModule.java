package com.example.leszek.simpledagger2mvp.dagger.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.ti_mvp_sample.TiMvpSampleApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class ApplicationModule {

    @NonNull
    private final Application application;

    public ApplicationModule(@NonNull Application application) {
        this.application = application;
    }

    @Provides
    public Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    public TiMvpSampleApplication provideTiMvpSampleApplication() {
        return (TiMvpSampleApplication) application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }
}

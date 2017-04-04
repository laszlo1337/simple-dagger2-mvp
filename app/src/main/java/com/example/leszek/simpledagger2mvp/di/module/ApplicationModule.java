package com.example.leszek.simpledagger2mvp.di.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.leszek.simpledagger2mvp.SimpleDagger2MvpApplication;

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
    public SimpleDagger2MvpApplication provideSimpleDagger2MvpApplication() {
        return (SimpleDagger2MvpApplication) application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }
}

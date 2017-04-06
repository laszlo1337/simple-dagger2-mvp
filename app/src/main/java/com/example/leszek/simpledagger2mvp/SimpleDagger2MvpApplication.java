package com.example.leszek.simpledagger2mvp;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.leszek.simpledagger2mvp.di.component.ApplicationComponent;
import com.example.leszek.simpledagger2mvp.di.component.DaggerApplicationComponent;
import com.example.leszek.simpledagger2mvp.di.module.ApplicationModule;
import com.example.leszek.simpledagger2mvp.di.module.CommonModule;

import timber.log.Timber;


public class SimpleDagger2MvpApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate(){
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .commonModule(new CommonModule("https://api.github.com/"))
                .build();

        Timber.plant(new Timber.DebugTree());
    }


    @NonNull
    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}

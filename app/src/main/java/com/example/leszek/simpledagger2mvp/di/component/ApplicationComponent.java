package com.example.leszek.simpledagger2mvp.di.component;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


import com.example.leszek.simpledagger2mvp.SimpleDagger2MvpApplication;
import com.example.leszek.simpledagger2mvp.di.module.ApplicationModule;
import com.example.leszek.simpledagger2mvp.di.module.CommonModule;
import com.example.leszek.simpledagger2mvp.di.qualifier.AuthenticationInterceptor;
import com.example.leszek.simpledagger2mvp.di.qualifier.CachedOkHttpClient;
import com.example.leszek.simpledagger2mvp.di.qualifier.CachedRetrofit;
import com.example.leszek.simpledagger2mvp.di.qualifier.NonCachedOkHttpClient;
import com.example.leszek.simpledagger2mvp.di.qualifier.NonCachedRetrofit;
import com.example.leszek.simpledagger2mvp.presentation.base.PresenterCache;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {ApplicationModule.class, CommonModule.class})
public interface ApplicationComponent {

    Context context();

    PresenterCache presenterCache();

    SimpleDagger2MvpApplication simpleDagger2MvpApplication();

    Application application();

    SharedPreferences sharedPreferences();

    Cache cache();

    ObjectMapper objectMapper();

    @AuthenticationInterceptor
    Interceptor authenticationInterceptor();

    @CachedOkHttpClient
    OkHttpClient cachedOkHttpClient();

    @NonCachedOkHttpClient
    OkHttpClient nonCachedOkHttpClient();

    @CachedRetrofit
    Retrofit cachedRetrofit();

    @NonCachedRetrofit
    Retrofit nonCachedRetrofit();

}

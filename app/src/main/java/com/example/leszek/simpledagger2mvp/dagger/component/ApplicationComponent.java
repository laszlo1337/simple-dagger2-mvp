package com.example.leszek.simpledagger2mvp.dagger.component;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.ti_mvp_sample.TiMvpSampleApplication;
import com.example.ti_mvp_sample.dagger.module.ApplicationModule;
import com.example.ti_mvp_sample.dagger.module.CommonModule;
import com.example.ti_mvp_sample.dagger.module.DatabaseModule;
import com.example.ti_mvp_sample.dagger.qualifier.AuthenticationInterceptor;
import com.example.ti_mvp_sample.dagger.qualifier.CachedOkHttpClient;
import com.example.ti_mvp_sample.dagger.qualifier.CachedRetrofit;
import com.example.ti_mvp_sample.dagger.qualifier.NonCachedOkHttpClient;
import com.example.ti_mvp_sample.dagger.qualifier.NonCachedRetrofit;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Singleton;

import dagger.Component;
import io.requery.Persistable;
import io.requery.reactivex.ReactiveEntityStore;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {ApplicationModule.class, CommonModule.class, DatabaseModule.class})
public interface ApplicationComponent {

    Context context();

    TiMvpSampleApplication TiMvpSampleApplication();

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

    ReactiveEntityStore<Persistable> database();
}

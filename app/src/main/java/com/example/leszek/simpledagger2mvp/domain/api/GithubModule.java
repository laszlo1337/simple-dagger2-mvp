package com.example.leszek.simpledagger2mvp.domain.api;

import com.example.leszek.simpledagger2mvp.di.qualifier.NonCachedRetrofit;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;


@Module
public class GithubModule {

    @Provides
    GithubInterface provideGithubInteface(@NonCachedRetrofit Retrofit retrofit){
        return retrofit.create(GithubInterface.class);
    }
}

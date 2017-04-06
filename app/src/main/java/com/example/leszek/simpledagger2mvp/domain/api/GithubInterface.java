package com.example.leszek.simpledagger2mvp.domain.api;

import com.example.leszek.simpledagger2mvp.domain.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface GithubInterface {

    @GET("users")
    Observable<List<User>> getUsers(@Query("per_page") int perPage, @Query("since") int lastUserId);

//    @GET("users/{user}")
//    Observable
}

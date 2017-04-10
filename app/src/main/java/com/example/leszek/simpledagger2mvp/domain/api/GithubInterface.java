package com.example.leszek.simpledagger2mvp.domain.api;

import com.example.leszek.simpledagger2mvp.domain.model.User;
import com.example.leszek.simpledagger2mvp.domain.model.UserDetails;
import com.example.leszek.simpledagger2mvp.domain.model.UserSearchResult;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface GithubInterface {

    @GET("users")
    Observable<List<User>> getUsers(@Query("per_page") int perPage, @Query("since") int lastUserId);

    @GET("search/users")
    Observable<UserSearchResult> searchUsers(@Query("q") String username, @Query("per_page") int perPage);

    @GET("users/{user}")
    Single<UserDetails> getUserDetails(@Path("user") String user);
}

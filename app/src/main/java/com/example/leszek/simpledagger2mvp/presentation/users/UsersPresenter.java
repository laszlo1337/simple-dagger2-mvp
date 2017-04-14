package com.example.leszek.simpledagger2mvp.presentation.users;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.leszek.simpledagger2mvp.domain.api.GithubInterface;
import com.example.leszek.simpledagger2mvp.domain.model.User;
import com.example.leszek.simpledagger2mvp.domain.model.UserSearchResult;
import com.example.leszek.simpledagger2mvp.presentation.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class UsersPresenter extends BasePresenter<UsersView> {

    private final static int USER_QUANTITY_PER_CALL = 30;
    private final static int USER_QUANTITY_PER_SEARCH = 100;

    private GithubInterface githubInterface;
    private String query;
    private List<UserModel> users;
    private int lastUserId;
    private boolean isSearchViewSelected;

    @Inject
    public UsersPresenter(GithubInterface githubInterface) {
        this.githubInterface = githubInterface;
    }

    @Override
    protected void activityCreated(Bundle bundle) {
        if (users == null){
            users = new ArrayList<>();
            getUsers(0);
        } else {
            if (getView() != null) {
                getView().setUsers(users);
            }
        }
    }

    public void loadMore() {
        if(!isSearchViewSelected){
            getUsers(lastUserId);
        }
    }

    public void getUsers(int lastUserId) {
        compositeDisposable.clear();
        compositeDisposable.add(githubInterface.getUsers(USER_QUANTITY_PER_CALL, lastUserId)
                .subscribeOn(Schedulers.io())
                .flatMapIterable(users -> users)
                .map(user -> new UserModel(user.getId(), user.getLogin(), user.getAvatarUrl()))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> {
                    if (getView() != null) {
                        this.users.addAll(users);
                        this.lastUserId = users.get(users.size() - 1).getId();
                        getView().updateUsers(users);
                    }
                }, e -> {
                    if (getView() != null) {
                        getView().showErrorMessage();
                    }
                }));
    }

    public void searchUsers(String searchedLogin) {
        compositeDisposable.add(githubInterface.searchUsers(searchedLogin, USER_QUANTITY_PER_SEARCH)
                .subscribeOn(Schedulers.io())
                .flatMapIterable(UserSearchResult::getItems)
                .map(user -> new UserModel(user.getId(), user.getLogin(), user.getAvatarUrl()))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userModels -> {
                    if (getView() != null) {
                        getView().setUsers(userModels);
                    }
                }, e -> {
                    if (getView() != null) {
                        getView().showErrorMessage();
                    }
                }));
    }

    public void searchViewSelected(boolean selected) {
        this.isSearchViewSelected = selected;
        lastUserId = 0;
        if(selected){
            searchUsers(query);
        } else {
            if (getView() != null) {
                getView().clearUsers();
            }
            getUsers(lastUserId);
        }
    }

    public void setQuery(String s) {
        query = s;
    }
}

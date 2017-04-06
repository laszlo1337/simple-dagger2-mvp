package com.example.leszek.simpledagger2mvp.presentation.users;

import android.support.annotation.Nullable;

import com.example.leszek.simpledagger2mvp.domain.api.GithubInterface;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.requery.query.Scalar;
import timber.log.Timber;


public class UsersPresenter {

    public static int USER_QUANTITY_PER_CALL = 16;

    private CompositeDisposable compositeDisposable;
    private GithubInterface githubInterface;

    private int lastUserId;

    @Nullable
    UsersView view;

    @Inject
    public UsersPresenter(GithubInterface githubInterface) {
        this.githubInterface = githubInterface;
        this.compositeDisposable = new CompositeDisposable();
    }

    public void attachView(UsersView usersView) {
        this.view = usersView;
        getUsers(0);
    }

    public void detachView() {
        this.view = null;
        this.compositeDisposable.clear();
    }

    public void loadMore() {
        getUsers(lastUserId);
    }

    public void getUsers(int lastUserId) {
        compositeDisposable.add(githubInterface.getUsers(USER_QUANTITY_PER_CALL, lastUserId)
                .subscribeOn(Schedulers.io())
                .flatMapIterable(users -> users)
                .map(user -> new UserModel(user.getId(), user.getLogin(), user.getAvatarUrl()))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> {
                    if (view != null) {
                        this.lastUserId = users.get(users.size() - 1).getId();
                        view.updateUsers(users);
                    }
                }, e -> {
                    if (view != null) {
                        view.showErrorMessage();
                    }
                }));

    }



}

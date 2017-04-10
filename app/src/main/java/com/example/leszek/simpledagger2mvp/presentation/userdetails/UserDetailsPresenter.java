package com.example.leszek.simpledagger2mvp.presentation.userdetails;

import com.example.leszek.simpledagger2mvp.domain.api.GithubInterface;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Leszek Janiszewski
 */

public class UserDetailsPresenter {

    UserDetailsView userDetailsView;
    GithubInterface githubInterface;
    CompositeDisposable compositeDisposable;

    @Inject
    public UserDetailsPresenter(GithubInterface githubInterface) {
        this.githubInterface = githubInterface;
        this.compositeDisposable = new CompositeDisposable();
    }

    public void attachView(UserDetailsView userDetailsView, String userLogin) {
        this.userDetailsView = userDetailsView;
        getUserDetails(userLogin);
    }

    public void detachView(){
        this.compositeDisposable.dispose();
        this.userDetailsView = null;
    }

    public void getUserDetails(String userLogin) {
        compositeDisposable.add(githubInterface.getUserDetails(userLogin)
                .subscribeOn(Schedulers.io())
                .map(user -> new UserDetailsModel(user.getLogin(), user.getId(), user.getAvatarUrl()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userDetailsModel ->
                                userDetailsView.setUserDetails(userDetailsModel),
                        throwable ->
                                userDetailsView.showErrorMessage()));
    }
}

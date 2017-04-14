package com.example.leszek.simpledagger2mvp.presentation.userdetails.di;

import com.example.leszek.simpledagger2mvp.di.component.ApplicationComponent;
import com.example.leszek.simpledagger2mvp.di.scope.ActivityScope;
import com.example.leszek.simpledagger2mvp.domain.api.GithubModule;
import com.example.leszek.simpledagger2mvp.presentation.base.PresenterComponent;
import com.example.leszek.simpledagger2mvp.presentation.userdetails.UserDetailsActivity;
import com.example.leszek.simpledagger2mvp.presentation.userdetails.UserDetailsPresenter;
import com.example.leszek.simpledagger2mvp.presentation.users.UsersActivity;
import com.example.leszek.simpledagger2mvp.presentation.users.di.UsersModule;

import dagger.Component;

/**
 * @author Leszek Janiszewski
 */

@ActivityScope
@Component(dependencies = {ApplicationComponent.class}, modules = {GithubModule.class})
public interface UserDetailsComponent extends PresenterComponent<UserDetailsPresenter> {
    void injectTo(UserDetailsActivity userDetailsActivity);
}

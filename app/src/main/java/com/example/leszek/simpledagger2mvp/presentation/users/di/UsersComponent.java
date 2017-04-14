package com.example.leszek.simpledagger2mvp.presentation.users.di;

import com.example.leszek.simpledagger2mvp.di.component.ApplicationComponent;
import com.example.leszek.simpledagger2mvp.di.scope.ActivityScope;
import com.example.leszek.simpledagger2mvp.domain.api.GithubModule;
import com.example.leszek.simpledagger2mvp.presentation.base.PresenterComponent;
import com.example.leszek.simpledagger2mvp.presentation.users.UsersActivity;
import com.example.leszek.simpledagger2mvp.presentation.users.UsersPresenter;

import dagger.Component;

/**
 * @author Leszek Janiszewski
 */

@ActivityScope
@Component(dependencies = {ApplicationComponent.class}, modules = {UsersModule.class, GithubModule.class})
public interface UsersComponent extends PresenterComponent<UsersPresenter> {
    void injectTo(UsersActivity usersActivity);
}

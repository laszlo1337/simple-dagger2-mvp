package com.example.leszek.simpledagger2mvp.presentation.users.di;

import com.example.leszek.simpledagger2mvp.di.component.ApplicationComponent;
import com.example.leszek.simpledagger2mvp.di.scope.ActivityScope;
import com.example.leszek.simpledagger2mvp.domain.api.GithubModule;
import com.example.leszek.simpledagger2mvp.presentation.users.UsersActivity;

import dagger.Component;

/**
 * @author Leszek Janiszewski
 */

@ActivityScope
@Component(dependencies = {ApplicationComponent.class}, modules = {UsersModule.class, GithubModule.class})
public interface UsersComponent {
    void injectTo(UsersActivity usersActivity);
}

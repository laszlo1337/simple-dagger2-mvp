package com.example.leszek.simpledagger2mvp.presentation.base;

/**
 * Base Dagger component to provide a {@link BasePresenter} .
 */
public interface PresenterComponent<T extends BasePresenter>
{
    T getPresenter();
}

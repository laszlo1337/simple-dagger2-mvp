package com.example.leszek.simpledagger2mvp.presentation.base;

/**
 * Base class for all view interfaces.
 */
public interface BaseView
{

    /**
     * Called by the presenter when it has aquired all models so the view can init the layout by
     * getting data from the presenter.
     */
    void created();
}

package com.example.leszek.simpledagger2mvp.presentation.userdetails;

import com.example.leszek.simpledagger2mvp.presentation.base.BaseView;

/**
 * @author Leszek Janiszewski
 */

public interface UserDetailsView extends BaseView {
    void setUserDetails(UserDetailsModel userDetails);
    void showErrorMessage();
}

package com.example.leszek.simpledagger2mvp.presentation.userdetails;

/**
 * @author Leszek Janiszewski
 */

public interface UserDetailsView {
    void setUserDetails(UserDetailsModel userDetails);
    void showErrorMessage();
}

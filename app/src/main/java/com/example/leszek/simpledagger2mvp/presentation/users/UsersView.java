package com.example.leszek.simpledagger2mvp.presentation.users;

import com.example.leszek.simpledagger2mvp.domain.model.User;

import java.util.List;



public interface UsersView {
    void updateUsers(List<UserModel> users);
    void showErrorMessage();
    void onClickListItem(String name);
}

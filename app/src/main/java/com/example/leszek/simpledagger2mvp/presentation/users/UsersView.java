package com.example.leszek.simpledagger2mvp.presentation.users;

import com.example.leszek.simpledagger2mvp.presentation.base.BaseView;

import java.util.List;



public interface UsersView extends BaseView{
    void setUsers(List<UserModel> users);
    void updateUsers(List<UserModel> users);
    void clearUsers();
    void showErrorMessage();
    void onClickListItem(String name);
}

package com.example.leszek.simpledagger2mvp.presentation.users;

import java.util.List;



public interface UsersView {
    void setUsers(List<UserModel> users);
    void updateUsers(List<UserModel> users);
    void clearUsers();
    void showErrorMessage();
    void onClickListItem(String name);
}

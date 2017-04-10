package com.example.leszek.simpledagger2mvp.presentation.userdetails;

/**
 * @author Leszek Janiszewski
 */

public class UserDetailsModel {

    private String login;
    private int id;
    private String avatarUrl;

    public UserDetailsModel(String login, int id, String avatarUrl) {
        this.login = login;
        this.id = id;
        this.avatarUrl = avatarUrl;
    }

    public String getLogin() {
        return login;
    }

    public int getId() {
        return id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}

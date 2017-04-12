package com.example.leszek.simpledagger2mvp.presentation.userdetails;

import com.example.leszek.simpledagger2mvp.domain.model.UserDetails;

/**
 * @author Leszek Janiszewski
 */

public class UserDetailsModel {

    private String login;
    private int id;
    private String avatarUrl;
    private String htmlUrl;
    private String type;
    private boolean siteAdmin;
    private String name;
    private String company;
    private String location;
    private String email;
    private boolean hireable;
    private String bio;
    private int followers;
    private String createdAt;
    private String blog;

    public UserDetailsModel(UserDetails user) {
        this.login = user.getLogin();
        this.id = user.getId();
        this.avatarUrl = user.getAvatarUrl();
        this.htmlUrl = user.getHtmlUrl();
        this.type = user.getType();
        this.siteAdmin = user.isSiteAdmin();
        this.name = user.getName();
        this.company = user.getCompany();
        this.location = user.getLocation();
        this.email = user.getEmail();
        this.hireable = user.isHireable();
        this.bio = user.getBio();
        this.followers = user.getFollowers();
        this.createdAt = user.getCreatedAt();
        this.blog = user.getBlog();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSiteAdmin() {
        return siteAdmin;
    }

    public void setSiteAdmin(boolean siteAdmin) {
        this.siteAdmin = siteAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isHireable() {
        return hireable;
    }

    public void setHireable(boolean hireable) {
        this.hireable = hireable;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }
}

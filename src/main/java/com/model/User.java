package com.model;

public class User {
    private Long userId;
    private String loginName;
    private String password;
    private String name;

    public User(){}

    public User(long userId, String loginName, String password, String name) {
        this.userId = userId;
        this.loginName = loginName;
        this.password = password;
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "userId:"+this.userId+",loginName:"+this.loginName+",password:"+this.password+",name:"+this.name;
    }
}


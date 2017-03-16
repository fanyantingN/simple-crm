package com.service;

import com.model.User;

import java.util.List;

public interface UserService {

    List<User> queryUser(final Long userId, final String loginName, int orderType);

    List<User> getUsers();

    User getUser(long userId);

    boolean addUser(String loginName, String name, String password);

    boolean delUser(long userId);

    boolean modifyUser(long userId, String loginName, String pwd, String name);
}

package com.service.impl;


import com.dao.UserDAO;
import com.model.User;
import com.service.UserService;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.cglib.core.Predicate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDAO userDAO;

    public List<User> queryUser(final Long userId, final String loginName, int orderType) {
        List<User> userList = new ArrayList(userDAO.getUsers());
        if(!StringUtils.isEmpty(loginName)){
            CollectionUtils.filter(userList, new Predicate() {
                public boolean evaluate(Object o) {
                    User user = (User) o;
                    boolean contain = user.getLoginName().indexOf(loginName) > -1;
                    return contain;
                }
            });
        }

        if(userId != null){
            CollectionUtils.filter(userList, new Predicate() {
                public boolean evaluate(Object o) {
                    User user = (User) o;
                    // 全量匹配 因为直接数据库的like是没法用上索引的。可以使用b-tree建立左值匹配。
                    return user.getUserId().equals(userId);
                }
            });
        }

        getOrderList(orderType, userList);

        return userList;
    }

    @Override
    public List<User> getUsers() {
        return userDAO.getUsers();
    }

    @Override
    public User getUser(long userId) {
        return userDAO.getUser(userId);
    }

    @Override
    public boolean addUser(String loginName, String name, String password) {
        return userDAO.addUser(loginName, name, password);
    }

    @Override
    public boolean delUser(long userId) {
        return userDAO.delUser(userId);
    }

    @Override
    public boolean modifyUser(long userId, String loginName, String pwd, String name) {
        return userDAO.modifyUser(userId, loginName, pwd, name);
    }

    private void getOrderList(int orderType, List<User> users) {
        switch (orderType){
            case 1:
                Collections.sort(users, new Comparator<User>() {
                    public int compare(User o1, User o2) {
                        long result = o1.getUserId() - o2.getUserId();
                        return convertIntResult(result);
                    }
                });
                break;
            case 2:
                Collections.sort(users, new Comparator<User>() {
                    public int compare(User o1, User o2) {
                        long result = o2.getUserId() - o1.getUserId();
                        return convertIntResult(result);
                    }
                });
                break;
            case 3:
                Collections.sort(users, new Comparator<User>() {
                    public int compare(User o1, User o2) {
                        return compareStr(o1.getLoginName(), o2.getLoginName());
                    }
                });
                break;
            case 4:
                Collections.sort(users, new Comparator<User>() {
                    public int compare(User o1, User o2) {
                        return compareStr(o2.getLoginName(), o1.getLoginName());
                    }
                });
                break;
            default:
                Collections.sort(users, new Comparator<User>() {
                    public int compare(User o1, User o2) {
                        long result = o1.getUserId() - o2.getUserId();
                        return convertIntResult(result);
                    }
                });
                break;
        }
    }

    private int convertIntResult(long result) {
        if(result > 0){
            return 1;
        }else if(result < 0){
            return -1;
        }else {
            return 0;
        }
    }

    private int compareStr(String str1, String str2){
        if(str1 == null || str2 == null || str1.length() < 1 || str1.length() < 1){
            return 0;
        }
        if(str1.length() > str2.length()){
            int result = compareEqualStr(str1.substring(0, str2.length()), str2);
            if(result == 0){
                return 1;
            }else{
                return result;
            }
        }
        if(str1.length() < str2.length()){
            int result = compareEqualStr(str1, str2.substring(0, str1.length()));
            if(result == 0){
                return -1;
            }else{
                return result;
            }
        }
        return compareEqualStr(str1, str2);
    }

    private int compareEqualStr(String str1, String str2){
        int result = 0;
        for(int i = 0; i < str1.length(); i++){
            if(str1.charAt(i) != str2.charAt(i)){
                result = str1.charAt(i) - str2.charAt(i);
                break;
            }
        }
        return result;
    }



    //    public static void main(String[] args) {
//        UserServiceImpl u = new UserServiceImpl();
//
//        System.out.println(u.compareStr("abc", "acc")); // -1
//        System.out.println(u.compareStr("ac", "acc")); // -1
//        System.out.println(u.compareStr("abc", "abc")); // 0
//        System.out.println(u.compareStr("abc", "ab")); // 1
//        System.out.println(u.compareStr("cbc", "bcc")); //1
//
//        System.out.println(u.queryUser(1, "", 4));
//    }

}

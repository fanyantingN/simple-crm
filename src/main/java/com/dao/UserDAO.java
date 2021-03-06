package com.dao;

import com.model.User;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class UserDAO {
    private static final List<User> users = new ArrayList<>();

    static {
        users.add(new User(1, "andy", "", "Andy"));
        users.add(new User(2, "carl", "", "Carl"));
        users.add(new User(3, "bruce", "", "Bruce"));
        users.add(new User(4, "dolly", "", "Dolly"));
        users.add(new User(5, "dolly2", "", "Dolly2"));
    }

    public List<User> getUsers(){
        return users;
    }

    public User getUser(long userId){
        for (User user : users) {
            if(userId == user.getUserId()){
                return user;
            }
        }
        return null;
    }

    public boolean addUser(String loginName, String name, String password) {
        users.add(new User(getMaxUserId()+1, loginName, password, name));
        return true;
    }

    public boolean delUser(long userId){
        Iterator it = users.iterator();
        while(it.hasNext()){
            User user = (User) it.next();
            if(user.getUserId().longValue() == userId){
                it.remove();
                return true;
            }
        }
        return false;
    }

    public boolean modifyUser(long userId, String loginName, String pwd, String name){
        Iterator it = users.iterator();
        while(it.hasNext()){
            User user = (User) it.next();
            if(user.getUserId().longValue() == userId){
                user.setLoginName(loginName);
                user.setPassword(pwd);
                user.setName(name);
                return true;
            }
        }
        return false;
    }

    private long getMaxUserId(){
        long max = 0;
        for(User user : users){
            if(user.getUserId().longValue() > max){
                max = user.getUserId().longValue();
            }
        }
        return max;
    }
}

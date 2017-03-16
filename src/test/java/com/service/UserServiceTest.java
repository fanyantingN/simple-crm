package com.service;

import com.dao.UserDAO;
import com.model.User;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:spring/spring-mvc.xml")
public class UserServiceTest {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserService userService;

    @org.junit.Test
    public void queryUser() throws Exception {
        List<User> users = userService.queryUser(1l, "", 1);
        List<User> users1 = userService.queryUser(null, "a", 1);
        List<User> users2 = userService.queryUser(null, "a", 2);
        assertEquals(1, users.size());
        assertEquals(2, users1.size());
        assertEquals(2, users2.size());
    }

    @org.junit.Test
    public void getUsers() throws Exception {
        List<User> userList = userDAO.getUsers();
        assertEquals(5, userList.size());
    }

    @org.junit.Test
    public void getUser() throws Exception {
        User user = userDAO.getUser(1);
        assertEquals("Andy", user.getName());
        assertEquals("andy", user.getLoginName());
    }

    @org.junit.Test
    public void addUser() throws Exception {
        List<User> userList = new ArrayList<>(userDAO.getUsers());
        System.out.println(userList);
        userDAO.addUser("fanyanting", "fanyt", "12");
        List<User> userList1 = userDAO.getUsers();
        System.out.println(userList1);
        assertEquals(userList.size() + 1, userList1.size());
    }

    @org.junit.Test
    public void delUser() throws Exception {
        List<User> userList = new ArrayList<>(userDAO.getUsers());
        userDAO.delUser(3);
        List<User> userList1 = userDAO.getUsers();
        assertEquals(userList.size() - 1, userList1.size());
    }

    @org.junit.Test
    public void modifyUser() throws Exception {
        boolean result = userDAO.modifyUser(2l, "test_modify", "111", "mmmm");
        assertEquals(true, result);
    }

}
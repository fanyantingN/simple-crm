package com.controller;

import com.dao.UserDAO;
import com.model.User;
import com.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("list")
    public String queryUser(){
        return "redirect:list/1?userId=&loginName=";
    }


    @RequestMapping("list/{orderType}")
    public ModelAndView queryUser(@PathVariable("orderType") int orderType, @RequestParam(value = "userId") String userId,
                                  @RequestParam(value = "loginName") String loginName){
        ModelAndView mv = new ModelAndView("list");
        Long userIdLong = null;
        if(!StringUtils.isEmpty(userId)){
            userIdLong = Long.parseLong(userId);
        }
        List<User> userList = userService.queryUser(userIdLong, loginName, orderType);
        mv.getModelMap().put("userList", userList);
        mv.getModelMap().put("orderType", orderType);
        mv.getModelMap().put("userId", userId);
        mv.getModelMap().put("loginName", loginName);
        return mv;
    }

    @RequestMapping("add")
    @ResponseBody
    public String addUser(@RequestParam("userId") String userId, @RequestParam("loginName") String loginName,
                          @RequestParam("name") String name, @RequestParam("password") String password){
        boolean result;
        if(StringUtils.isEmpty(userId)){
            result = UserDAO.addUser(loginName, name, password);
        }else{
            result = UserDAO.modifyUser(Long.parseLong(userId), loginName, password, name);
        }
        return result ? "success" : "failed";
    }

    @RequestMapping("delete")
    @ResponseBody
    public String deleteUser(@RequestParam("userId") String userId){
        boolean result = false;
        if(!StringUtils.isEmpty(userId)){
            result = UserDAO.delUser(Long.parseLong(userId));
        }
        return result ? "success" : "failed";
    }


}
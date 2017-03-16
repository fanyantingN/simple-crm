package com.controller;

import com.alibaba.fastjson.JSON;
import com.model.AjaxResponse;
import com.model.User;
import com.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if(!StringUtils.isEmpty(userId) && validNumber(userId)){
            userIdLong = Long.parseLong(userId);
        }
        List<User> userList = userService.queryUser(userIdLong, loginName, orderType);
        mv.getModelMap().put("userList", userList);
        mv.getModelMap().put("orderType", orderType);
        mv.getModelMap().put("userId", userId);
        mv.getModelMap().put("loginName", loginName);
        return mv;
    }

    private boolean validNumber(@RequestParam(value = "userId") String userId) {
        String reg = "^[0-9]*$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(userId);
        return matcher.matches();
    }

    @RequestMapping("aNewUser.html")
    public ModelAndView addNewUer(@RequestParam("userId") String userId){
        ModelAndView mv = new ModelAndView("aNewUser");
        if(!StringUtils.isEmpty(userId)){
            User user = userService.getUser(new Long(userId).longValue());
            mv.getModelMap().put("user", user);
        }
        return mv;
    }

    /**
     * 新增和修改用户接口
     * @param userId
     * @param loginName
     * @param name
     * @param password
     * @return
     */
    @RequestMapping(value="add", method = {RequestMethod.POST})
    @ResponseBody
    public String addUser(@RequestParam("userId") String userId, @RequestParam("loginName") String loginName,
                          @RequestParam("name") String name, @RequestParam("password") String password){
        AjaxResponse ajaxResponse = new AjaxResponse();
        boolean result;
        if (StringUtils.isEmpty(userId)) {
            result = userService.addUser(loginName, name, password);
        } else {
            result = userService.modifyUser(Long.parseLong(userId), loginName, password, name);
        }
        if (!result) {
            ajaxResponse.setStatus(300);
        }
        return JSON.toJSONString(ajaxResponse);
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "delete.json", method = {RequestMethod.POST}, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String deleteUser(@RequestParam("userId") long userId){
        AjaxResponse<List<User>> response = new AjaxResponse<>();
        boolean result = false;
        if (userId != 0) {
            result = userService.delUser(userId);
        }
        if (result) {
            response.setData(userService.getUsers());
        }else{
            response.setStatus(300);
        }
        return JSON.toJSONString(response);
    }


}

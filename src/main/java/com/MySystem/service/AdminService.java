package com.MySystem.service;

import com.MySystem.pojo.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface AdminService {
    //用户登录
    String login(UserInfo user, HttpServletRequest request);

    //邮箱登录
    String emailLogin(String email, HttpServletRequest request);
    //用户注册
    String register(UserInfo user);

    //用户名输入框失去焦点事件
    String focusAjax(UserInfo user);

    //发送邮箱验证码
    HashMap<String, Object> sendEmail(String email);

    //保存用户的头像
    String saveHead(UserInfo user,HttpServletRequest request);

    //根据ID修改用户名
    String changeName(UserInfo user,HttpServletRequest request);

    //根据ID修改用户名
    String changeEmail(UserInfo user,HttpServletRequest request);

    //根据ID修改密码
    String changePwd(UserInfo user,HttpServletRequest request);
}

package com.MySystem.controller;

import com.MySystem.pojo.UserInfo;

import com.MySystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequestMapping("index")
public class IndexController {
    @Autowired
    AdminService adminService;

    //获取图片上传的配置路径
    @Value("${file.address}")
    String fileAdress;

    //创建redis库的操作对象
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    //用户访问的图片路径
    @Value("${file.staticPath}")
    String upload;

    //访问用户主界面
    @RequestMapping("/homePage")
    public String homePage() {
        return "home/homepage";
    }

    //访问欢迎页面
    @RequestMapping("/welcome")
    public String welcome() {
        return "home/welcome";
    }

    //访问账号管理界面
    @RequestMapping("/account")
    public String account() {
        return "home/account";
    }

    //保存修改的用户头像
    @RequestMapping("/saveHead")
    @ResponseBody//ajax
    public HashMap<String, Object> saveHead(UserInfo user, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        System.out.println(user);
        String info = adminService.saveHead(user, request);
        map.put("info", info);
        return map;
    }

    //获取session中的user对象
    @RequestMapping("/getSession")
    @ResponseBody
    public HashMap<String, Object> getSession(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession();
        UserInfo user = (UserInfo) session.getAttribute("user");
        map.put("user", user);
        return map;
    }

    //打开修改用户名页面
    @RequestMapping("/changeName")
    public String changeName() {
        return "home/changeName";
    }

    //打开修改邮箱页面
    @RequestMapping("/changeEmail")
    public String changeEmail() {
        return "home/changeEmail";
    }

    //保存修改用户名
    @RequestMapping("/nameAjax")
    @ResponseBody
    public HashMap<String, Object> nameAjax(UserInfo user, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        String info = adminService.changeName(user, request);
        map.put("info", info);
        return map;
    }

    //保存修改邮箱
    @RequestMapping("/emailAjax")
    @ResponseBody
    public HashMap<String, Object> emailAjax(UserInfo user, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        String info = adminService.changeEmail(user, request);
        map.put("info", info);
        return map;
    }

    //修改密码
    @RequestMapping("/pwdAjax")
    @ResponseBody
    public HashMap<String, Object> pwdAjax(UserInfo user, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        String info = adminService.changePwd(user, request);
        map.put("info", info);
        return map;
    }
}

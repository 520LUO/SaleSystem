package com.MySystem.controller;

import com.MySystem.pojo.UserInfo;
import com.MySystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    //获取图片上传的配置路径
    @Value("${file.address}")
    String fileAdress;

    //创建redis库的操作对象
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    //用户访问的图片路径
    @Value("${file.staticPath}")
    String upload;


    //测试
    @RequestMapping("/test")
    public String test() {
        return "login";
    }

    //访问登录界面
    @RequestMapping("/loginPage")
    public String loginPage() {
        return "admin/login";
    }

    //ajax登录
    @RequestMapping("/loginAjax")
    @ResponseBody//把数据返回且转换成 json 格式
    public HashMap<String, Object> loginajax(UserInfo user, HttpServletRequest request) {

        HashMap<String, Object> map = new HashMap<>();
        String info = adminService.login(user, request);//传数据到逻辑层
        map.put("info", info);
        return map;
    }

    //访问注册界面
    @RequestMapping("/registerPage")
    public String registerPage() {
        return "admin/register";
    }



    //ajax注册
    @RequestMapping("/registerAjax")
    @ResponseBody
    public HashMap<String, Object> registerAjax(UserInfo user) {
        HashMap<String, Object> map = new HashMap<>();
        String info = adminService.register(user);
        map.put("info", info);
        return map;
    }

    //用户名输入框失去焦点事件,判断用户名是否重名
    @RequestMapping("/focusAjax")
    @ResponseBody
    public HashMap<String, Object> focusAjax(UserInfo user) {
        HashMap<String, Object> map = new HashMap<>();
        String info = adminService.focusAjax(user);
        map.put("info", info);
        return map;
    }

    //图片上传
    //用户名输入框失去焦点事件,判断用户名是否重名
    @RequestMapping("/uploadAjax")
    @ResponseBody
    public HashMap<String, Object> uploadAjax(MultipartFile file) {
        HashMap<String, Object> map = new HashMap<>();
        //上传文件
        try {
            //定义一个文件上传目录
            String timeDir;
            //获取时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            timeDir = sdf.format(new Date());

            //定义文件上传的前缀
            String pre;
            //为了保证文件上传到服务器文件名是唯一的
            pre = UUID.randomUUID() + "";//生成比较大的随机数转为字符串
            //获取文件的后缀名 jpg
            String hou = "";
            if (file != null) {
                String originalName = file.getOriginalFilename();
                hou = originalName.substring(originalName.lastIndexOf(".") + 1);//截取'.'后的后缀名

            }
            //定义文件上传的全路径
            String filePath = fileAdress + "/" + timeDir + "/" + pre + "." + hou;
            //创建file对象
            File f = new File(filePath);
            //判断目录是否存在，不存在就创建目录
            if (!f.isDirectory()) {
                f.mkdirs();
            }
            //上传文件
            file.transferTo(f);
            map.put("code", 0);//设置上传成功的提示信息
            //返回前端 用户访问图片的路径
            String path = upload + "/" + timeDir + "/" + pre + "." + hou;//完整的图片地址
            map.put("src", path);
            return map;

        } catch (IOException e) {
            e.printStackTrace();
            map.put("code", 1);//设置上传失败的提示信息
            return map;

        }
    }

    //处理邮件发送的请求
    @RequestMapping("/sendEmail")
    @ResponseBody//ajax
    public HashMap<String, Object> sendEmail(String email) {

        return adminService.sendEmail(email);
    }

    //验证邮件发送的验证码
    @RequestMapping("/verifyCode")
    @ResponseBody//ajax
    public HashMap<String, Object> verifyCode(String code) {
        HashMap<String, Object> map = new HashMap<>();
        String valCode= (String) redisTemplate.opsForValue().get("valCode");

        if (code.equals(valCode)){
            map.put("info","验证成功");
        }else{
            map.put("info","验证失败");
        }
        return map;
    }
    /*
     *
     * 4.处理邮件登录的请求
     *
     * */
    @RequestMapping("/emailLogin")
    @ResponseBody
    public  HashMap<String,Object> emailLogin(String email,String code, HttpServletRequest request){
        HashMap<String ,Object> map = new HashMap<>();
        //通过Redis取值
        String valCode =(String) redisTemplate.opsForValue().get("valCode");
        if(code.equals(valCode)){
            String info =adminService.emailLogin(email,request);
            map.put("info",info);
        }
        else{
            map.put("info","验证码输入错误");
        }
        return map;
    }

}

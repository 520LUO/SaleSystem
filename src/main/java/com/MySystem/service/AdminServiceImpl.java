package com.MySystem.service;

import com.MySystem.dao.UserInfoDao;
import com.MySystem.pojo.UserInfo;
import com.MySystem.util.MdFive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service //把此类的对象创建交给spring
public class AdminServiceImpl implements AdminService {
    //创建redis库的操作对象
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    UserInfoDao userInfoDao;

    @Autowired
    MdFive mdFive;

    //获取发件人邮箱
    @Value("${spring.mail.username}")
    String sendEmail;

    //创建发送邮件的对象
    @Autowired
    JavaMailSender javaMailSender;


    /**
     * 检查用户是否已经被锁定，如果是，返回剩余锁定时间，如果否，返回-1
     *
     * @param username username
     * @return 时间
     */
    private long getUserLoginTimeLock(String username) {
        String key = "user:" + username + ":lockTime";
        //获取key 过期时间
        long lockTime = redisTemplate.getExpire(key, TimeUnit.SECONDS);

        if (lockTime > 0) {//查询用户是否已经被锁定，如果是，返回剩余锁定时间，如果否，返回-1
            return lockTime;
        } else {
            return -1;
        }
    }

    /**
     * 获取当前用户已失败次数
     *
     * @param username username
     * @return 已失败次数
     */
    private int getUserFailCount(String username) {
        String key = "user:" + username + ":failCount";
        //从redis中获取当前用户已失败次数
        Object object = redisTemplate.opsForValue().get(key);
        if (object != null) {
            return (int) object;
        } else {
            return -1;
        }
    }

    /**
     * 设置失败次数
     *
     * @param username username
     */
    private void setFailCount(String username) {
        //获取当前用户已失败次数
        long count = this.getUserFailCount(username);
        String key = "user:" + username + ":failCount";
        if (count < 0) {//判断redis中是否有该用户的失败登陆次数，如果没有，设置为1，过期时间为1分钟，如果有，则次数+1
            redisTemplate.opsForValue().set(key, 1, 60, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().increment(key, new Double(1));
        }
    }


    //登录方法
    @Override
    public String login(UserInfo user, HttpServletRequest request) {
        int count = userInfoDao.valName(user);
        if (count == 0) {
            return "用户不存在";
        } else {
            //先判断用户是否被锁定
            if (this.getUserLoginTimeLock(user.getUserName()) < 0) {
                Integer salt = userInfoDao.selectByUserName(user);
                //加密用户输入的密码
                String pwd = mdFive.encrypt(user.getUserPwd(), salt.toString());//两个参数（密码、盐值）
                HttpSession session = request.getSession();
                //把加过密的密码传入数据层
                user.setUserPwd(pwd);
                //查询数据库的登录方法
                UserInfo userInfo = userInfoDao.login(user);
                session.setAttribute("user", userInfo);
                //如果查询到值，userinfo 就非空
                if (userInfo != null) {
                    return "登录成功";
                } else {//密码输入错误
                    //设置密码输入失败次数
                    setFailCount(user.getUserName());
                    //获取密码失败次数
                    int num = getUserFailCount(user.getUserName());
                    //定义允许用户失败的次数
                    int cc = 3;
                    //剩余失败次数 =cc-num
                    int rest = cc - num;
                    if (rest > 0) {
                        return "你输入的密码错误" + num + "次，还剩余" + rest + "次";

                    } else {
                        String key = "user:" + user.getUserName() + ":lockTime";
                        //设置失效的时间
                        redisTemplate.opsForValue().set(key, 22, 60, TimeUnit.SECONDS);
                        return "你输入密码的次数超过" + cc + "次，您的账户已经被锁定";
                    }
                }
            } else {
                return "用户账户已经被锁定，请 1 分钟后再尝试登陆";
            }
        }


    }

    //注册方法
    @Override
    public String register(UserInfo user) {
        Integer salt = userInfoDao.selectMax();
        salt++;//将编号作为盐值
        String pwd = mdFive.encrypt(user.getUserPwd(), salt.toString());
        user.setUserPwd(pwd);
        System.out.println(user);
        //判断账号是否被注册
        int num = userInfoDao.valName(user);
        if (num > 0) {
            return "用户名已经被注册";
        } else {
            int n = userInfoDao.register(user);
            if (n > 0) {
                return "注册成功";
            }
        }
        return "注册失败";
    }

    //用户名输入框失去焦点事件
    public String focusAjax(UserInfo user) {
        //判断账号是否被注册
        int num = userInfoDao.valName(user);
        if (num > 0) {
            return "已被注册";
        } else {
            return "未被注册";
        }

    }

    //发送邮件
    @Override
    public HashMap<String, Object> sendEmail(String email) {

        HashMap<String, Object> map = new HashMap<>();
        try {
            //创建普通邮件对象
            SimpleMailMessage message = new SimpleMailMessage();
            //设置发件人邮箱
            message.setFrom(sendEmail);
            //设置收件人邮箱
            message.setTo(email);
            //设置邮件主题
            message.setSubject("沃得码进销存系统验证码");
            // 生成随机验证码
            Random rd = new Random();
            String valCode = rd.nextInt(8999) + 1000+"";
            //设置邮件正文
            message.setText("您的验证码是：" + valCode);
            //发送邮件
            javaMailSender.send(message);

            //把验证码存到redis中
            redisTemplate.opsForValue().set("valCode", valCode, 180, TimeUnit.SECONDS);
            map.put("info", "发送成功");
            return map;

        } catch (Exception e) {
            System.out.println("发送邮件时发生异常！");
            e.printStackTrace();
        }
        map.put("info", "发送邮件异常");
        return map;
    }

    //保存用户的头像
    @Override
    public String saveHead(UserInfo user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserInfo u = (UserInfo) session.getAttribute("user");
        //把 userId 存入 user 中，进行 url 修改
        user.setUserId(u.getUserId());
        int num = userInfoDao.updateHead(user);
        if (num > 0) {
            return "保存成功";
        } else {
            return "保存失败";
        }
    }

    //修改用户名
    @Override
    public String changeName(UserInfo user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        //查询用户名是否重名
        int count = userInfoDao.valName(user);
        if (count > 0) {
            return "用户名已存在";
        } else {
            int num = userInfoDao.changeName(user);
            if (num > 0) {
                //修改用户名后，及时更新 session 中的 user
                UserInfo userInfo = userInfoDao.selectById(user);
                session.setAttribute("user", userInfo);
                return "修改成功";
            } else {
                return "修改失败";
            }
        }
    }

    //修改用户的邮箱
    @Override
    public String changeEmail(UserInfo user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int num = userInfoDao.changeEmail(user);
        if (num > 0) {
            UserInfo userInfo = userInfoDao.selectById(user);
            session.setAttribute("user", userInfo);
            return "修改成功";
        } else {
            return "修改失败";
        }
    }

    //修改密码
    @Override
    public String changePwd(UserInfo user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        String pwd = userInfo.getUserPwd();
        Integer salt = userInfo.getUserId();
        String oldPwd = mdFive.encrypt(user.getOldPwd(), salt.toString());
        //将输入的旧密码与session中用户的正确密码比较
        if (pwd.equals(oldPwd)) {
            //加密新密码
            String newPwd = mdFive.encrypt(user.getNewPwd(), salt.toString());
            userInfo.setUserPwd(newPwd);
            session.setAttribute("user", userInfo);
            //修改数据库中的密码
            int num=userInfoDao.changePwd(userInfo);
            if (num>0){
                return "修改成功";
            }else {
                return "修改失败";
            }
        }else return "旧密码输入错误";
    }

    @Override
    public String emailLogin(String email, HttpServletRequest request) {

        //查询数据层的登录方法，并拿到返回值
        UserInfo userInfo = userInfoDao.emailLogin(email);
        if (userInfo != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", userInfo);
            return "登录成功";
        }
        return "邮箱未注册,";
    }


}

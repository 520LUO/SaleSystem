package com.MySystem.service;

import com.MySystem.dao.UserInfoDao;
import com.MySystem.pojo.UserInfo;
import com.MySystem.util.MdFive;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class SuperServiceImpl implements SuperService {

    //创建redis库的操作对象
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    UserInfoDao userInfoDao;

    @Autowired
    MdFive mdFive;


    @Override
    public HashMap<String, Object> selectPage(UserInfo user) {
        String condition = user.getCondition();
        String convalue = user.getConValue();
        List<UserInfo> list;
        HashMap<String, Object> map = new HashMap<>();
        //1.设置分页参数
        PageHelper.startPage(user.getPage(), user.getRow());
        //判断用户输入的查询条件是否有值
        if ("".equals(convalue)) {
            list = userInfoDao.select();

        } else {
            if ("编号".equals(condition)) {
                //字符串转换成数字
                user.setUserId(Integer.parseInt(convalue));
                list = userInfoDao.findByUserId(user);
            } else if ("用户名".equals(condition)) {
                user.setUserName(convalue);
                list = userInfoDao.findByUserName(user);
            } else if ("职位".equals(condition)) {
                user.setUserPosition(convalue);
                list = userInfoDao.findByPosition(user);
            } else if ("权限等级".equals(condition)) {
                user.setUserLevel(Integer.parseInt(convalue));
                list = userInfoDao.findByLevel(user);
            } else {
                list = userInfoDao.select();
            }
        }
        //3.把查询数据转换成分页对象
        PageInfo<UserInfo> page = new PageInfo<>(list);

        //获取分页的当前页的集合
        map.put("list", page.getList());
        //获取总的条数
        map.put("total", page.getTotal());
        //获取总的页数
        map.put("totalPage", page.getPages());
        //上一页
        if (page.getPrePage() == 0) {
            map.put("pre", 1);
        } else {
            map.put("pre", page.getPrePage());
        }

        //下一页
        if (page.getNextPage() == 0) {//判断下一页为0 ，就将下一页停在最后一页
            map.put("next", page.getPages());
        } else {
            map.put("next", page.getNextPage());
        }
        //当前页
        map.put("cur", page.getPageNum());
        return map;
    }

    //通过 userId 进行查询用户完整信息
    @Override
    public UserInfo selectByUserId(UserInfo user) {

        return userInfoDao.selectById(user);
    }

    //超级管理员批量修改用户信息
    @Override
    public String update(UserInfo user) {
        int num;//更改操作的返回值
        //先判断用户名是否存在
        int number = userInfoDao.valName(user);
        UserInfo userInfo = userInfoDao.selectById(user);
        if (number > 0) {
            //再判断存在的用户名是否是它本身
            if (userInfo.getUserName().equals(user.getUserName())) {
                num = userInfoDao.update(user);
                if (num > 0) {
                    return "修改成功";
                } else return "修改失败";
            }
            return "用户名已存在";
        } else {
            num = userInfoDao.update(user);
            if (num > 0) {
                return "修改成功";
            }
        }
        return "修改失败";
    }

    //超级管理员删除用户信息
    @Override
    public String delete(UserInfo user) {
        int num = userInfoDao.deleteById(user);
        if (num > 0) {
            return "删除成功";
        } else return "删除失败";
    }

    //超级管理员添加信息
    @Override
    public String insert(UserInfo user) {
        //查询用户名是否重名
        int number = userInfoDao.valName(user);
        if (number > 0) {
            return "用户名已存在";
        } else {
            //设置默认图片
            if (user.getUrl().length() == 0) {
                user.setUrl("/upload\\2021-02-14\\d1bf6f94-afff-4c39-86a1-9dcd93e3ecd3.jpg");
            }
            //获取盐值
            Integer salt = userInfoDao.selectMax();
            salt++;
            //密码加密
            String pwd = mdFive.encrypt(user.getUserPwd(), salt.toString());
            user.setUserPwd(pwd);
            //添加到数据库
            int num = userInfoDao.insert(user);
            if (num > 0) {
                return "添加成功";
            } else {
                return "添加失败";
            }
        }

    }

    //excel导出
    @Override
    public List<UserInfo> userExcel() {
        return userInfoDao.select();
    }
}

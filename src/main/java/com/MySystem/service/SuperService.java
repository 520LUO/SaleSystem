package com.MySystem.service;

import com.MySystem.pojo.UserInfo;

import java.util.HashMap;
import java.util.List;

public interface SuperService {
    //分页查询
    HashMap<String, Object> selectPage(UserInfo user);

    //通过 userId 进行查询
    UserInfo selectByUserId(UserInfo user);

    //超级管理员批量修改用户信息
    String update(UserInfo user);

    //超级管理员删除用户信息
    String delete(UserInfo user);

    //超级管理员添加数据
    String insert(UserInfo user);

    //查询所有信息，用List存储
    List<UserInfo> userExcel();


}

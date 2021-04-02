package com.MySystem.service;

import com.MySystem.pojo.SupplyInfo;
import com.MySystem.pojo.UserInfo;

import java.util.HashMap;
import java.util.List;

public interface SupplyService {
    //供货商页面
    HashMap<String, Object> selectPage(SupplyInfo supply);

    //添加供货商信息
    String insert(SupplyInfo supply);

    //导出excel,查询所有信息
    List<SupplyInfo> userExcel();

    //根据ID查找完整信息
    SupplyInfo selectById(SupplyInfo supply);

    //修改的请求
    String update(SupplyInfo supply);

    //删除请求
    String delete(SupplyInfo supply);
}

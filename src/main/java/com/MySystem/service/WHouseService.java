package com.MySystem.service;

import com.MySystem.pojo.WareHouse;

import java.util.HashMap;
import java.util.List;

public interface WHouseService {
    //仓库信息页面
    HashMap<String, Object> selectPage(WareHouse ware);

    //添加仓库信息
    String insert(WareHouse ware);

    //导出excel,查询所有信息
    List<WareHouse> userExcel();

    //根据ID查找完整信息
    WareHouse selectById(WareHouse ware);

    //修改的请求
    String update(WareHouse ware);

    //删除请求
    String delete(WareHouse ware);
}

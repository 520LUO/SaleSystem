package com.MySystem.service;

import com.MySystem.pojo.StoreGoods;
import com.MySystem.pojo.OutGoods;

import java.util.HashMap;
import java.util.List;

public interface StoreService {
    //库存信息页面
    HashMap<String, Object> selectPage(StoreGoods store);

    //添加库存商品信息
    String insert(StoreGoods store);

    //导出excel,查询所有信息
    List<StoreGoods> userExcel();

    //根据ID查找完整信息
    StoreGoods selectById(StoreGoods store);

    //修改的请求
    String update(StoreGoods store);

    //删除请求
    String delete(StoreGoods store);

    //获取柱状图所需的数据
    HashMap<String, Object> histogram(StoreGoods store);
}

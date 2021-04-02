package com.MySystem.service;

import com.MySystem.pojo.IncomeGoods;
import com.MySystem.pojo.OutGoods;
import com.MySystem.pojo.StoreGoods;

import java.util.HashMap;
import java.util.List;

public interface GoodsService {
    //入库信息页面
    HashMap<String, Object> selectPage(IncomeGoods income);

    //添加入库商品信息
    String insert(IncomeGoods income);

    //导出excel,查询所有信息
    List<IncomeGoods> userExcel();

    //根据ID查找完整信息
    IncomeGoods selectById(IncomeGoods income);

    //根据商品编号查找库存商品完整信息
    StoreGoods selectByIgNum(IncomeGoods income);

    //修改的请求
    String update(IncomeGoods income);

    //删除请求
    String delete(IncomeGoods income);


    //出库商品信息页面
    HashMap<String, Object> selectOutPage(OutGoods out);

    //添加出库商品信息
    String outInsert(OutGoods out);


    //根据ID查找出库商品完整信息
    OutGoods selectByOutId(OutGoods out);

    //根据商品编号查找库存商品完整信息
    StoreGoods selectByOgNum(OutGoods out);

    //出库商品修改的请求
    String outUpdate(OutGoods out);

    //出库商品删除请求
    String outDelete(OutGoods out);

    //出商品导出excel查询
    List<OutGoods> outGoodsExcel();
}

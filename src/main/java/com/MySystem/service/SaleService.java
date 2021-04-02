package com.MySystem.service;

import com.MySystem.dao.SaleGoodsDao;
import com.MySystem.pojo.SaleRecord;
import com.MySystem.pojo.StoreGoods;
import com.MySystem.pojo.WareHouse;

import java.util.HashMap;
import java.util.List;

public interface SaleService {
    //库存信息页面
    HashMap<String, Object> selectPage(SaleRecord record);

    //添加库存商品信息
    String insert(SaleRecord record);

    //导出excel,查询所有信息
    List<SaleRecord> userExcel();

    //根据ID查找完整信息
    SaleRecord selectById(SaleRecord record);

    //修改的请求
    String update(SaleRecord record);


    //获取所有的仓库地址
    List<WareHouse> getArea();

    //获取所有的包含地址经纬度的销售记录
    List<HashMap<String, Object>> getRecord(SaleRecord record);

    //根据saleNum查找完整的库存商品
    StoreGoods selectFromStore(String saleNum);

    //折线图
    HashMap<String, Object> lineChart(SaleRecord record);
}

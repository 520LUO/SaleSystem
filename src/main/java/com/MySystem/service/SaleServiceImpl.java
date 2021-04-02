package com.MySystem.service;


import com.MySystem.dao.SaleGoodsDao;
import com.MySystem.pojo.AdressInfo;
import com.MySystem.pojo.SaleRecord;
import com.MySystem.pojo.StoreGoods;
import com.MySystem.pojo.WareHouse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {
    //导入库存dao
    @Autowired
    SaleGoodsDao saleGoodsDao;


    @Override
    public HashMap<String, Object> selectPage(SaleRecord record) {
        Double totalMoney = 0.0;//库存商品总金额
        List<SaleRecord> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        String condition = record.getCondition();
        String convalue = record.getConvalue();
        PageHelper.startPage(record.getPage(), record.getRow());
        //判端查询条件
        if ("".equals(convalue)) {
            list = saleGoodsDao.select();
        } else {
            if ("商品编号".equals(condition)) {
                //字符串转换成数字
                record.setSaleNum(convalue);
                list = saleGoodsDao.findByNum(record);
            } else if ("销售时间".equals(condition)) {
                record.setSaleTime(convalue);
                list = saleGoodsDao.findByTime(record);
            } else if ("买家电话".equals(condition)) {
                record.setBuyTel(convalue);
                list = saleGoodsDao.findByTel(record);
            } else {
                list = saleGoodsDao.select();
            }
        }
        //把查询结果转换成分页对象
        PageInfo<SaleRecord> page = new PageInfo<>(list);
        map.put("list", page.getList());
        map.put("total", page.getTotal());
        map.put("totalPage", page.getPages());
        //计算总金额
        for (SaleRecord s : saleGoodsDao.select()) {
            totalMoney += s.getSaleTotal();
        }
        map.put("money", totalMoney + " 元");
        map.put("total", page.getTotal() + " 条");
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
        //当前.页
        map.put("cur", page.getPageNum());

        return map;
    }

    @Override
    public String insert(SaleRecord record) {
        int num = saleGoodsDao.insert(record);
        if (num > 0) {
            return "添加成功";
        } else return "添加失败";
    }

    //excel
    @Override
    public List<SaleRecord> userExcel() {
        return saleGoodsDao.select();
    }

    //根据编号查询完整信息
    @Override
    public SaleRecord selectById(SaleRecord record) {
        return saleGoodsDao.findBysaleId(record);
    }

    //修改
    @Override
    public String update(SaleRecord record) {
        int num = saleGoodsDao.update(record);
        if (num > 0) {
            return "修改成功";
        } else {
            return "修改失败";
        }
    }


    //获取所有的仓库地址
    @Override
    public List<WareHouse> getArea() {
        return saleGoodsDao.selectArea();
    }

    //获取所有的包含地址经纬度的销售记录，生成商品流动图
    @Override
    public List<HashMap<String, Object>> getRecord(SaleRecord record) {
        List<SaleRecord> list = new ArrayList<>();
        List<HashMap<String, Object>> allList = new ArrayList<>();
        if ("销售日期".equals(record.getCondition())) {
            //根据时间查询
            list = saleGoodsDao.selectByTime(record);
        } else {
            list = saleGoodsDao.select();
        }

        for (SaleRecord sale : list) {
            //将每个销售记录中地址的经纬度查询出来
            HashMap<String, Object> map = new HashMap<>();
            AdressInfo storeAddress = saleGoodsDao.selectAddress(sale.getStoreArea());
            AdressInfo saleAddress = saleGoodsDao.selectAddress(sale.getSaleArea());
            if (saleAddress != null) {
                map.put("record", sale);
                map.put("storeAddress", storeAddress);
                map.put("saleAddress", saleAddress);
                allList.add(map);
            }
        }
        return allList;
    }

    //根据saleNum查找完整的库存商品
    @Override
    public StoreGoods selectFromStore(String saleNum) {

        return saleGoodsDao.selectFromStore(saleNum);
    }

    //折线图
    @Override
    public HashMap<String, Object> lineChart(SaleRecord record) {
        HashMap<String, Object> map = new HashMap<>();
        List<SaleRecord> list = new ArrayList<>();
        boolean count = "数量".equals(record.getCondition());
        boolean money = "总金额".equals(record.getCondition());
        //查询数据库
        if ("全部记录".equals(record.getConvalue())) {
            list = saleGoodsDao.select();
        } else if ("销售日期".equals(record.getConvalue())) {
            if ("".equals(record.getSaleTime())) {
                list = saleGoodsDao.select();
            } else {
                list = saleGoodsDao.selectByTime(record);
            }
        }

        List<String> list_x = new ArrayList<>();//x轴
        List<Object> list_y = new ArrayList<>();//y轴
        //构建柱状图数据
        if (count) {//根据商品数量显示
            for (SaleRecord s : list) {
                list_y.add(s.getSaleCount());
                list_x.add(s.getSaleName());
            }
        } else if (money) {//根据商品总价显示
            for (SaleRecord s : list) {
                list_y.add(s.getSaleTotal());
                list_x.add(s.getSaleName());
            }
        }
        map.put("value_x", list_x);
        map.put("value_y", list_y);
        return map;

    }

}

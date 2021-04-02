package com.MySystem.service;


import com.MySystem.dao.StoreGoodsDao;
import com.MySystem.pojo.StoreGoods;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {
    //导入库存dao
    @Autowired
    StoreGoodsDao storeGoodsDao;


    @Override
    public HashMap<String, Object> selectPage(StoreGoods store) {
        Double totalMoney = 0.0;//库存商品总金额
        List<StoreGoods> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        String condition = store.getCondition();
        String convalue = store.getConvalue();
        PageHelper.startPage(store.getPage(), store.getRow());
        //判端查询条件
        if ("".equals(convalue)) {
            list = storeGoodsDao.select();
        } else {
            if ("编号".equals(condition)) {
                //字符串转换成数字
                store.setSgId(Integer.parseInt(convalue));
                list = storeGoodsDao.findById(store);
            } else if ("商品类别".equals(condition)) {
                store.setSgClass(convalue);
                list = storeGoodsDao.findByClass(store);
            } else if ("供货商".equals(condition)) {
                store.setSgSupply(convalue);
                list = storeGoodsDao.findBySupply(store);
            } else {
                list = storeGoodsDao.select();
            }
        }
        //把查询结果转换成分页对象
        PageInfo<StoreGoods> page = new PageInfo<>(list);
        map.put("list", page.getList());
        map.put("total", page.getTotal());
        map.put("totalPage", page.getPages());
        //计算总金额
        for (StoreGoods s :storeGoodsDao.select() ) {
            totalMoney +=s.getSgTotal();
        }
        map.put("money",totalMoney+" 元");
        map.put("totalClass",page.getTotal()+" 种");
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
    public String insert(StoreGoods store) {
        int num = storeGoodsDao.insert(store);
        if (num > 0) {
            return "添加成功";
        } else return "添加失败";
    }

    @Override
    public List<StoreGoods> userExcel() {
        return storeGoodsDao.select();
    }

    //根据编号查询完整信息
    @Override
    public StoreGoods selectById(StoreGoods store) {
        return storeGoodsDao.findBysgId(store);
    }

    //修改
    @Override
    public String update(StoreGoods store) {
        int num = storeGoodsDao.update(store);
        if (num > 0) {
            return "修改成功";
        } else {
            return "修改失败";
        }
    }

    //删除
    @Override
    public String delete(StoreGoods store) {
        int num = storeGoodsDao.deleteById(store);
        if (num > 0) {
            return "删除成功";
        } else {
            return "删除失败";
        }
    }
    
    //柱状图显示
    @Override
        public HashMap<String, Object> histogram(StoreGoods store) {
            HashMap<String, Object> map = new HashMap<>();
            List<StoreGoods> list=new ArrayList<>();
            boolean count = "数量".equals(store.getCondition());
            boolean money = "总金额".equals(store.getCondition());
            //查询数据库
        if ("全部商品".equals(store.getConvalue())){
            list=storeGoodsDao.select();
        }else if ("入库日期".equals(store.getConvalue())){
            if ("".equals(store.getSgTime())){
                list=storeGoodsDao.select();
            }else{
                list = storeGoodsDao.selectGraph(store);
            }
        }

            List<String> list_x = new ArrayList<>();//x轴
            List<Object> list_y = new ArrayList<>();//y轴
            //构建柱状图数据
            if (count){//根据商品数量显示
                for (StoreGoods s:list){
                    list_y.add(s.getSgCount());
                    list_x.add(s.getSgName());
                }
            }else if (money){//根据商品总价显示
            for (StoreGoods s:list){
                list_y.add(s.getSgTotal());
                list_x.add(s.getSgName());
            }
        }

            map.put("value_x", list_x);
            map.put("value_y", list_y);

            return map;
        }
        
}

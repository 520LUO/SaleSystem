package com.MySystem.service;


import com.MySystem.dao.WareHouseDao;
import com.MySystem.pojo.WareHouse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class WHouseServiceImpl implements WHouseService {

    @Autowired
    WareHouseDao wareHouseDao;

    @Override
    public HashMap<String, Object> selectPage(WareHouse ware) {
        List<WareHouse> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        String condition = ware.getCondition();
        String convalue = ware.getConvalue();
        PageHelper.startPage(ware.getPage(), ware.getRow());
        //判端查询条件
        if ("".equals(convalue)) {
            list = wareHouseDao.select();
        } else {
            if ("编号".equals(condition)) {
                //字符串转换成数字
                ware.setWhId(Integer.parseInt(convalue));
                list = wareHouseDao.findBywhId(ware);
            } else if ("仓库类别".equals(condition)) {
                ware.setWhClass(convalue);
                list = wareHouseDao.findBywhClass(ware);
            }  else {
                list = wareHouseDao.select();
            }
        }
        //把查询结果转换成分页对象
        PageInfo<WareHouse> page = new PageInfo<>(list);
        map.put("list", page.getList());
        map.put("total", page.getTotal());
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

    @Override
    public String insert(WareHouse ware) {
        int number=wareHouseDao.valNum(ware);
        if (number>0){
            return "编号已存在";
        }else{
            int num = wareHouseDao.insert(ware);
            if (num > 0) {
                return "添加成功";
            } else return "添加失败";
        }
    }

    @Override
    public List<WareHouse> userExcel() {
        return wareHouseDao.select();
    }

    //根据编号查询完整信息
    @Override
    public WareHouse selectById(WareHouse ware) {
        return wareHouseDao.findById(ware);
    }

    //修改
    @Override
    public String update(WareHouse ware) {
        int num = wareHouseDao.update(ware);
        if (num > 0) {
            return "修改成功";
        } else {
            return "修改失败";
        }
    }

    //删除
    @Override
    public String delete(WareHouse ware) {
        int num = wareHouseDao.deleteById(ware);
        if (num > 0) {
            return "删除成功";
        } else {
            return "删除失败";
        }
    }
}

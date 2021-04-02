package com.MySystem.service;


import com.MySystem.dao.SupplyInfoDao;
import com.MySystem.pojo.SupplyInfo;
import com.MySystem.pojo.UserInfo;
import com.MySystem.util.MdFive;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SupplyServiceImpl implements SupplyService {

    @Autowired
    SupplyInfoDao supplyInfoDao;

    @Override
    public HashMap<String, Object> selectPage(SupplyInfo supply) {
        List<SupplyInfo> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        String condition = supply.getCondition();
        String convalue = supply.getConvalue();
        PageHelper.startPage(supply.getPage(), supply.getRow());
        //判端查询条件
        if ("".equals(convalue)) {
            list = supplyInfoDao.select();
        } else {
            if ("编号".equals(condition)) {
                //字符串转换成数字
                supply.setSupId(Integer.parseInt(convalue));
                list = supplyInfoDao.findBySupId(supply);
            } else if ("代理人".equals(condition)) {
                supply.setSupAgent(convalue);
                list = supplyInfoDao.findByAgentName(supply);
            } else if ("性别".equals(condition)) {
                supply.setSex(convalue);
                list = supplyInfoDao.findBySex(supply);
            } else if ("联系电话".equals(condition)) {
                supply.setSupTel(convalue);
                list = supplyInfoDao.findByTel(supply);
            } else if ("证件名称".equals(condition)) {
                supply.setCertName(convalue);
                list = supplyInfoDao.findByCertName(supply);
            } else if ("签发时间".equals(condition)) {
                supply.setStartTime(convalue);
                list = supplyInfoDao.findByStartTime(supply);
            } else {
                list = supplyInfoDao.select();

            }
        }
        //把查询结果转换成分页对象
        PageInfo<SupplyInfo> page = new PageInfo<>(list);
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
    public String insert(SupplyInfo supply) {
        int num = supplyInfoDao.insert(supply);
        if (num > 0) {
            return "添加成功";
        } else return "添加失败";
    }

    @Override
    public List<SupplyInfo> userExcel() {
        return supplyInfoDao.select();
    }

    //根据编号查询完整信息
    @Override
    public SupplyInfo selectById(SupplyInfo supply) {
        return supplyInfoDao.findById(supply);
    }

    //修改
    @Override
    public String update(SupplyInfo supply) {
        int num = supplyInfoDao.update(supply);
        if (num > 0) {
            return "修改成功";
        } else {
            return "修改失败";
        }
    }

    //删除
    @Override
    public String delete(SupplyInfo supply) {
        int num = supplyInfoDao.deleteById(supply);
        if (num > 0) {
            return "删除成功";
        } else {
            return "删除失败";
        }
    }
}

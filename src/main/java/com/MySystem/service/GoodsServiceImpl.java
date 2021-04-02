package com.MySystem.service;


import com.MySystem.dao.IncomeGoodsDao;
import com.MySystem.dao.OutGoodsDao;
import com.MySystem.pojo.IncomeGoods;
import com.MySystem.pojo.OutGoods;
import com.MySystem.pojo.StoreGoods;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    //导入入库dao
    @Autowired
    IncomeGoodsDao inComeGoodsDao;
    //导入出库dao
    @Autowired
    OutGoodsDao outGoodsDao;

    @Override
    public HashMap<String, Object> selectPage(IncomeGoods income) {
        List<IncomeGoods> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        String condition = income.getCondition();
        String convalue = income.getConvalue();
        PageHelper.startPage(income.getPage(), income.getRow());
        //判端查询条件
        if ("".equals(convalue)) {
            list = inComeGoodsDao.select();
        } else {
            if ("编号".equals(condition)) {
                //字符串转换成数字
                income.setIgId(Integer.parseInt(convalue));
                list = inComeGoodsDao.findById(income);
            } else if ("商品类别".equals(condition)) {
                income.setIgClass(convalue);
                list = inComeGoodsDao.findByClass(income);
            } else if ("入库时间".equals(condition)) {
                income.setIgTime(convalue);
                list = inComeGoodsDao.findByTime(income);
            } else if ("供货商".equals(condition)) {
                income.setIgSupply(convalue);
                list = inComeGoodsDao.findBySupply(income);
            } else {
                list = inComeGoodsDao.select();
            }
        }
        //把查询结果转换成分页对象
        PageInfo<IncomeGoods> page = new PageInfo<>(list);
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
        //当前.页
        map.put("cur", page.getPageNum());

        return map;
    }

    //添加入库信息
    @Override
    public String insert(IncomeGoods income) {
        int num, num2;
        //判断库存商品是否存在
        StoreGoods store = inComeGoodsDao.findByIgNum(income);
        if (store == null) {
            num = inComeGoodsDao.insert(income);
            num2 = inComeGoodsDao.insertStore(income);
            if (num > 0 && num2 > 0) {
                return "添加成功";
            } else return "添加失败";
        } else {
            num = inComeGoodsDao.insert(income);//添加入库记录
            int count = store.getSgCount() + income.getIgCount();//添加库存记录
            double total = store.getSgTotal() + income.getIgTotal();
            store.setSgCount(count);
            store.setSgTotal(total);
            store.setSgTime(income.getIgTime());
            num2 = inComeGoodsDao.updateStore(store);
            if (num > 0 && num2 > 0) {
                return "添加成功";
            } else return "添加失败";
        }

    }

    //导出出库记录
    @Override
    public List<IncomeGoods> userExcel() {
        return inComeGoodsDao.select();
    }

    //根据编号查询完整信息
    @Override
    public IncomeGoods selectById(IncomeGoods income) {
        return inComeGoodsDao.findByIgId(income);
    }

    //根据商品编号查找库存商品完整信息
    @Override
    public StoreGoods selectByIgNum(IncomeGoods income) {
        return inComeGoodsDao.findByIgNum(income);
    }

    //修改
    @Override
    public String update(IncomeGoods income) {
        int num = inComeGoodsDao.update(income);
        if (num > 0) {
            return "修改成功";
        } else {
            return "修改失败";
        }
    }

    //删除
    @Override
    public String delete(IncomeGoods income) {
        int num = inComeGoodsDao.deleteById(income);
        if (num > 0) {
            return "删除成功";
        } else {
            return "删除失败";
        }
    }

    //出库商品信息页面
    @Override
    public HashMap<String, Object> selectOutPage(OutGoods out) {
        List<OutGoods> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        String condition = out.getCondition();
        String convalue = out.getConvalue();
        PageHelper.startPage(out.getPage(), out.getRow());
        //判端查询条件
        if ("".equals(convalue)) {
            list = outGoodsDao.select();
        } else {
            if ("编号".equals(condition)) {
                //字符串转换成数字
                out.setOgId(Integer.parseInt(convalue));
                list = outGoodsDao.findById(out);
            } else if ("商品类别".equals(condition)) {
                out.setOgClass(convalue);
                list = outGoodsDao.findByClass(out);
            } else if ("入库时间".equals(condition)) {
                out.setOgTime(convalue);
                list = outGoodsDao.findByTime(out);
            } else if ("供货商".equals(condition)) {
                out.setOgSupply(convalue);
                list = outGoodsDao.findBySupply(out);
            } else {
                list = outGoodsDao.select();
            }
        }
        //把查询结果转换成分页对象
        PageInfo<OutGoods> page = new PageInfo<>(list);
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

    //添加出库商品信息
    @Override
    public String outInsert(OutGoods out) {
        int num, num2;
        //判断库存商品是否存在
        StoreGoods store = outGoodsDao.findByOgNum(out);
        if (store == null) {
            return "库存商品不存在";
        } else {
            if (out.getOgCount() > store.getSgCount()) {//判断出库商品的数量是否大于库存数量
                return "库存商品不足";
            } else {
                int count = store.getSgCount() - out.getOgCount();//修改库存商品
                double money = store.getSgTotal() - out.getOgTotal();
                store.setSgCount(count);
                store.setSgTotal(money);
                num = outGoodsDao.insert(out);
                num2 = outGoodsDao.updateStore(store);
                if (num > 0 && num2 > 0) {
                    return "添加成功";
                } else return "添加失败";
            }
        }

    }

    //根据ID查找出库商品完整信息
    @Override
    public OutGoods selectByOutId(OutGoods out) {
        return outGoodsDao.findByogId(out);
    }

    //根据商品编号查找库存商品完整信息
    @Override
    public StoreGoods selectByOgNum(OutGoods out) {
        return outGoodsDao.findByOgNum(out);
    }

    //出库商品修改的请求
    @Override
    public String outUpdate(OutGoods out) {
        int num = outGoodsDao.update(out);
        if (num > 0) {
            return "修改成功";
        } else {
            return "修改失败";
        }
    }

    //出库商品删除请求
    @Override
    public String outDelete(OutGoods out) {
        int num = outGoodsDao.deleteById(out);
        if (num > 0) {
            return "删除成功";
        } else {
            return "删除失败";
        }
    }

    //出商品导出excel查询
    @Override
    public List<OutGoods> outGoodsExcel() {
        return outGoodsDao.select();
    }
}

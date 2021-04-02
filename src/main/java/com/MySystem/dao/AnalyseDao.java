package com.MySystem.dao;


import com.MySystem.pojo.Analysis;
import com.MySystem.pojo.IncomeGoods;
import com.MySystem.pojo.OutGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AnalyseDao {

    //查询入库信息月统计
    @Select("select * from IncomeGoods where igTime like #{time}")
    List<IncomeGoods> incomeGraph(Analysis analys);

    //查询出库信息月统计
    @Select("select * from OutGoods where ogTime like #{time}")
    List<OutGoods> outGraph(Analysis analys);
}

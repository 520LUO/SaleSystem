package com.MySystem.dao;


import com.MySystem.pojo.OutGoods;
import com.MySystem.pojo.StoreGoods;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper //这个接口是执行mybatis 的数据库操作
public interface OutGoodsDao {
    //分页查询
    @Select("select * from OutGoods ")
    List<OutGoods> select();

    //根据编号查询
    @Select("select * from OutGoods where ogId=#{ogId}")
    List<OutGoods> findById(OutGoods out);

    //根据入库时间查询
    @Select("select * from OutGoods where ogTime=#{ogTime}")
    List<OutGoods> findByTime(OutGoods out);

    //根据供货商查询
    @Select("select * from OutGoods where ogSupply=#{ogSupply}")
    List<OutGoods> findBySupply(OutGoods out);

    //根据仓库类别查询
    @Select("select * from OutGoods where ogClass=#{ogClass}")
    List<OutGoods> findByClass(OutGoods out);

    //录入出库商品信息
    @Insert("insert into OutGoods(ogNum,ogName,ogClass,ogPrice,ogCount,ogTotal,ogTime,ogPerson,ogSupply) " +
            "VALUES(#{ogNum},#{ogName},#{ogClass},#{ogPrice},#{ogCount},#{ogTotal},#{ogTime},#{ogPerson},#{ogSupply})")
    int insert(OutGoods out);

    //根据ID查找完整信息
    @Select("select * from OutGoods where ogId=#{ogId}")
    OutGoods findByogId(OutGoods out);

    //根据ID修改
    @Update("update OutGoods set ogNum=#{ogNum},ogName=#{ogName},ogClass=#{ogClass},ogPrice=#{ogPrice}," +
            "ogCount=#{ogCount},ogTotal=#{ogTotal},ogTime=#{ogTime},ogPerson=#{ogPerson},ogSupply=#{ogSupply} where ogId=#{ogId}")
    int update(OutGoods out);

    //根据编号删除信息
    @Update("delete from OutGoods  where ogId=#{ogId}")
    int deleteById(OutGoods out);

    //根据出库商品编号查找完整库存商品信息
    @Select("select * from StoreGoods where sgNum=#{ogNum}")
    StoreGoods findByOgNum(OutGoods out);

    //根据出库商品修改库存商品的数量和价格
    @Update("update StoreGoods set sgCount=#{sgCount},sgTotal=#{sgTotal} where sgId=#{sgId}")
    int updateStore(StoreGoods store);
}

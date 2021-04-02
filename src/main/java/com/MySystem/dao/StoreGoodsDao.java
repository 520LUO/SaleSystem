package com.MySystem.dao;


import com.MySystem.pojo.StoreGoods;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper //这个接口是执行mybatis 的数据库操作
public interface StoreGoodsDao {
    //分页查询
    @Select("select * from StoreGoods ")
    List<StoreGoods> select();

    //根据编号查询
    @Select("select * from StoreGoods where sgId=#{sgId}")
    List<StoreGoods> findById(StoreGoods store);
    
    //根据供货商查询
    @Select("select * from StoreGoods where sgSupply=#{sgSupply}")
    List<StoreGoods> findBySupply(StoreGoods store);

    //根据仓库类别查询
    @Select("select * from StoreGoods where sgClass=#{sgClass}")
    List<StoreGoods> findByClass(StoreGoods store);

    //录入商品信息
    @Insert("insert into StoreGoods(sgNum,sgClass,sgPrice,sgCount,sgTotal,sgTime,sgPerson,sgSupply) " +
            "VALUES(#{sgNum},#{sgClass},#{sgPrice},#{sgCount},#{sgTotal},#{sgTime},#{sgPerson},#{sgSupply})")
    int insert(StoreGoods store);

    //根据ID查找完整信息
    @Select("select * from StoreGoods where sgId=#{sgId}")
    StoreGoods findBysgId(StoreGoods store);

    //根据ID修改
    @Update("update StoreGoods set sgNum=#{sgNum},sgClass=#{sgClass},sgPrice=#{sgPrice}," +
            "sgCount=#{sgCount},sgTotal=#{sgTotal},sgTime=#{sgTime},sgPerson=#{sgPerson},sgSupply=#{sgSupply} where sgId=#{sgId}")
    int update(StoreGoods store);

    //根据编号删除信息
    @Update("delete from StoreGoods  where sgId=#{sgId}")
    int deleteById(StoreGoods store);

    //条件查询，根据时间查询柱状图所需的数据
    @Select("select * from StoreGoods where sgTime=#{sgTime}")
    List<StoreGoods> selectGraph(StoreGoods store);
}

package com.MySystem.dao;


import com.MySystem.pojo.IncomeGoods;
import com.MySystem.pojo.StoreGoods;
import com.MySystem.pojo.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper //这个接口是执行mybatis 的数据库操作
public interface IncomeGoodsDao {

    //分页查询
    @Select("select * from IncomeGoods ")
    List<IncomeGoods> select();

    //根据编号查询
    @Select("select * from IncomeGoods where igId=#{igId}")
    List<IncomeGoods> findById(IncomeGoods income);

    //根据入库时间查询
    @Select("select * from IncomeGoods where igTime=#{igTime}")
    List<IncomeGoods> findByTime(IncomeGoods income);

    //根据供货商查询
    @Select("select * from IncomeGoods where igSupply=#{igSupply}")
    List<IncomeGoods> findBySupply(IncomeGoods income);

    //根据商品类别查询
    @Select("select * from IncomeGoods where igClass=#{igClass}")
    List<IncomeGoods> findByClass(IncomeGoods income);

    //录入商品信息
    @Insert("insert into IncomeGoods(igNum,igName,igClass,igPrice,igCount,igTotal,igTime,igPerson,igSupply) " +
            "VALUES(#{igNum},#{igName},#{igClass},#{igPrice},#{igCount},#{igTotal},#{igTime},#{igPerson},#{igSupply})")
    int insert(IncomeGoods income);

    //更改库存商品信息
    @Insert("insert into StoreGoods(sgNum,sgName,sgClass,sgPrice,sgCount,sgTotal,sgTime,sgSupply) " +
            "VALUES(#{igNum},#{igName},#{igClass},#{igPrice},#{igCount},#{igTotal},#{igTime},#{igSupply})")
    int insertStore(IncomeGoods income);

    //根据ID查找完整信息
    @Select("select * from IncomeGoods where igId=#{igId}")
    IncomeGoods findByIgId(IncomeGoods income);

    //根据ID修改
    @Update("update IncomeGoods set igNum=#{igNum},igName=#{igName},igClass=#{igClass},igPrice=#{igPrice}," +
            "igCount=#{igCount},igTotal=#{igTotal},igTime=#{igTime},igPerson=#{igPerson},igSupply=#{igSupply}  where igId=#{igId}")
    int update(IncomeGoods income);

    //根据编号删除信息
    @Update("delete from IncomeGoods where igId=#{igId}")
    int deleteById(IncomeGoods income);

    //根据商品编号查询完整的库存信息
    @Select("select * from StoreGoods where sgNum=#{igNum}")
    StoreGoods findByIgNum(IncomeGoods income);

    //根据ID修改库存的商品数量、总价和入库时间
    @Update("update StoreGoods set sgCount=#{sgCount},sgTotal=#{sgTotal},sgTime=#{sgTime} where sgId=#{sgId}")
    int updateStore(StoreGoods store);


}

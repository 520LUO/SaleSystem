package com.MySystem.dao;


import com.MySystem.pojo.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper //这个接口是执行mybatis 的数据库操作
public interface SaleGoodsDao {
    //分页查询
    @Select("select * from SaleRecord ")
    List<SaleRecord> select();

    //根据商品编号查询
    @Select("select * from SaleRecord where saleNum=#{saleNum}")
    List<SaleRecord> findByNum(SaleRecord record);
    //根据销售时间查询
    @Select("select * from SaleRecord where saleTime=#{saleTime}")
    List<SaleRecord> findByTime(SaleRecord record);
    //根据买家电话查询
    @Select("select * from SaleRecord where buyTel=#{buyTel}")
    List<SaleRecord> findByTel(SaleRecord record);



    //录入销售记录信息
    @Insert("insert into SaleRecord(saleNum,saleName,salePrice,saleCount,saleTotal,saleTime,buyTel,buyEmail,storeArea,saleArea) " +
            "VALUES(#{saleNum},#{saleName},#{salePrice},#{saleCount},#{saleTotal},#{saleTime},#{buyTel},#{buyEmail},#{storeArea},#{saleArea})")
    int insert(SaleRecord record);

    //根据ID查找完整信息
    @Select("select * from SaleRecord where saleId=#{saleId}")
    SaleRecord findBysaleId(SaleRecord record);

    //根据ID修改
    @Update("update SaleRecord set saleNum=#{saleNum},saleName=#{saleName},salePrice=#{salePrice},saleCount=#{saleCount},saleTotal=#{saleTotal}," +
            "saleTime=#{saleTime},buyTel=#{buyTel},buyEmail=#{buyEmail},storeArea=#{storeArea},saleArea=#{saleArea}  where saleId=#{saleId}")
    int update(SaleRecord record);

    //根据编号删除信息
    @Update("delete from SaleRecord  where saleId=#{saleId}")
    int deleteById(SaleRecord record);

    //条件查询，根据时间查询图表所需的数据
    @Select("select * from SaleRecord where saleTime=#{saleTime}")
    List<SaleRecord> selectByTime(SaleRecord record);

    //查询所有仓库地址
    @Select("select distinct(whAdress) from WareHouse")
    List<WareHouse> selectArea();

    //根据地址查询经纬度
    @Select("select * from ChinaInfo where name=#{address}")
    AdressInfo selectAddress(String address);

    //根据saleNum查找完整的库存商品
    @Select("select * from StoreGoods where sgNum=#{saleNum}")
    StoreGoods selectFromStore(String saleNum);
}

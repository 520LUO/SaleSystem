package com.MySystem.dao;


import com.MySystem.pojo.SupplyInfo;
import com.MySystem.pojo.UserInfo;
import com.MySystem.pojo.WareHouse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper //这个接口是执行mybatis 的数据库操作
public interface WareHouseDao {

    //分页查询
    @Select("select * from WareHouse ")
    List<WareHouse> select();

    //根据编号查询
    @Select("select * from WareHouse where whId=#{whId}")
    List<WareHouse> findBywhId(WareHouse ware);


    //根据仓库类别查询
    @Select("select * from WareHouse where whClass=#{whClass}")
    List<WareHouse> findBywhClass(WareHouse ware);

    //添加仓库信息
    @Insert("insert into WareHouse(whNum,whAdress,whArea,shelves,whClass,whNote) VALUES(#{whNum},#{whAdress},#{whArea},#{shelves},#{whClass},#{whNote})")
    int insert(WareHouse ware);

    //根据ID查找完整信息
    @Select("select * from WareHouse where whId=#{whId}")
    WareHouse findById(WareHouse ware);

    //根据ID修改
    @Update("update WareHouse set whNum=#{whNum},whAdress=#{whAdress},whArea=#{whArea}," +
            "shelves=#{shelves},whClass=#{whClass},whNote=#{whNote} where whId=#{whId}")
    int update(WareHouse ware);

    //根据编号删除信息
    @Update("delete from WareHouse  where whId=#{whId}")
    int deleteById(WareHouse ware);

    //查找仓库编号是否重复
    @Select("select count(*) from WareHouse where whNum=#{whNum}")
    int valNum(WareHouse ware);
}

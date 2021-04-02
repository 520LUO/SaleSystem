package com.MySystem.dao;


import com.MySystem.pojo.SupplyInfo;
import com.MySystem.pojo.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper //这个接口是执行mybatis 的数据库操作
public interface SupplyInfoDao {

    //分页查询
    @Select("select * from SupplyInfo ")
    List<SupplyInfo> select();

    //根据编号查询
    @Select("select * from SupplyInfo where supId=#{supId}")
    List<SupplyInfo> findBySupId(SupplyInfo supply);

    //根据供货商代理人查询
    @Select("select * from SupplyInfo where supAgent=#{supAgent}")
    List<SupplyInfo> findByAgentName(SupplyInfo supply);

    //根据性别查询
    @Select("select * from SupplyInfo where sex=#{sex}")
    List<SupplyInfo> findBySex(SupplyInfo supply);

    //根据联系电话查询
    @Select("select * from SupplyInfo where supTel=#{supTel}")
    List<SupplyInfo> findByTel(SupplyInfo supply);

    //根据证件名称查询
    @Select("select * from SupplyInfo where certName=#{certName}")
    List<SupplyInfo> findByCertName(SupplyInfo supply);

    //根据签发时间查询
    @Select("select * from SupplyInfo where startTime=#{startTime}")
    List<SupplyInfo> findByStartTime(SupplyInfo supply);

    //添加供货商信息
    @Insert("insert into SupplyInfo(supAgent,sex,supTel,supEmail,certName,certUrl,certNum,startTime,validTime)" +
            " VALUES(#{supAgent},#{sex},#{supTel},#{supEmail},#{certName},#{certUrl},#{certNum},#{startTime},#{validTime})")
    int insert(SupplyInfo supply);

    //根据ID查找完整信息
    @Select("select * from SupplyInfo where supId=#{supId}")
    SupplyInfo findById(SupplyInfo supply);

    //根据ID修改
    @Update("update SupplyInfo set supAgent=#{supAgent},sex=#{sex},supTel=#{supTel},supEmail=#{supEmail},certName=#{certName}" +
            ",certUrl=#{certUrl},certNum=#{certNum},startTime=#{startTime},validTime=#{validTime} where supId=#{supId}")
    int update(SupplyInfo supply);

    //根据编号删除信息
    @Update("delete from SupplyInfo  where supId=#{supId}")
    int deleteById(SupplyInfo supply);
}

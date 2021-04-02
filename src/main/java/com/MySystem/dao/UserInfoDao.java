package com.MySystem.dao;

import com.MySystem.pojo.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper //这个接口是执行mybatis 的数据库操作
public interface UserInfoDao {
    //查询登录
    @Select("select * from AdminInfo where userName=#{userName} and userPwd=#{userPwd}")
    UserInfo login(UserInfo user);

    //邮箱登录
    @Select("SELECT * FROM AdminInfo WHERE userEmail=#{email}")
    UserInfo emailLogin(String email);
    //注册
    @Insert("insert into AdminInfo(userName,userPwd,url,userEmail) VALUES(#{userName},#{userPwd},#{url},#{userEmail})")
    int register(UserInfo user);

    //查询最大 ID 值作为盐值
    @Select("select max(userId) from AdminInfo")
    int selectMax();

    //查询用户名是否重复
    @Select("select count(*) from AdminInfo where userName=#{userName}")
    int valName(UserInfo user);

    //通过用户名查找uerId
    @Select("select userId from AdminInfo where userName=#{userName}")
    int selectByUserName(UserInfo user);

    //修改头像
    @Update("update AdminInfo set url=#{url} where userId=#{userId}")
    int updateHead(UserInfo user);

    //根据ID修改用户名
    @Update("update AdminInfo set userName=#{userName} where userId=#{userId}")
    int changeName(UserInfo user);

    //通过id查询完整的信息
    @Select("select * from AdminInfo where userId=#{userId}")
    UserInfo selectById(UserInfo user);

    //根据ID修改邮箱
    @Update("update AdminInfo set userEmail=#{userEmail} where userId=#{userId}")
    int changeEmail(UserInfo user);

    //根据ID修改密码
    @Update("update AdminInfo set userPwd=#{userPwd} where userId=#{userId}")
    int changePwd(UserInfo user);

    //根据ID修改用户名、邮箱、职位、权限等级、图片路径
    @Update("update AdminInfo set url=#{url},userName=#{userName},userEmail=#{userEmail},userPosition=#{userPosition},userLevel=#{userLevel} where userId=#{userId}")
    int update(UserInfo user);

    //根据编号删除用户信息
    @Update("delete from AdminInfo  where userId=#{userId}")
    int deleteById(UserInfo user);

    //分页查询
    @Select("select * from AdminInfo ")
    List<UserInfo> select();

    //根据编号查询
    @Select("select * from AdminInfo where userId=#{userId}")
    List<UserInfo> findByUserId(UserInfo user);

    //根据用户名查询
    @Select("select * from AdminInfo where userName=#{userName}")
    List<UserInfo> findByUserName(UserInfo user);

    //根据职位查询
    @Select("select * from AdminInfo where userPosition=#{userPosition}")
    List<UserInfo> findByPosition(UserInfo user);

    //根据权限等级查询
    @Select("select * from AdminInfo where userLevel=#{userLevel}")
    List<UserInfo> findByLevel(UserInfo user);

    //超级管理员添加用户信息
    @Insert("insert into AdminInfo(userName,userPwd,url,userEmail,userPosition,userLevel) VALUES(#{userName},#{userPwd},#{url},#{userEmail},#{userPosition},#{userLevel})")
    int insert(UserInfo user);
}

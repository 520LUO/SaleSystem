package com.MySystem.pojo;

import java.io.Serializable;

public class UserInfo extends MyPage implements Serializable {//Serializable 序列化此类

    private int userId;
    private String userName;
    private String userPwd;
    //描述修改密码的旧密码参数
    private String oldPwd;
    private String newPwd;
    //查询条件condition参数
    private String condition;
    //查询条件conValue参数
    private String conValue;
    //图片保存的地址
    private String url;
    //    //管理员的等级，1为超级管理员、2为普通管理员。默认为2
    private int userLevel;
    //管理员的职位
    private String userPosition;
    //管理员的邮箱
    private String userEmail;


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getConValue() {
        return conValue;
    }

    public void setConValue(String conValue) {
        this.conValue = conValue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", oldPwd='" + oldPwd + '\'' +
                ", newPwd='" + newPwd + '\'' +
                ", condition='" + condition + '\'' +
                ", conValue='" + conValue + '\'' +
                ", url='" + url + '\'' +
                ", userLevel=" + userLevel +
                ", userPosition='" + userPosition + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}

package com.MySystem.pojo;

public class WareHouse extends MyPage {
    private int whId;         //编号
    private String whNum;     //仓库编号
    private String whAdress;  //仓库地址
    private String whArea;    //仓库面积
    private String shelves;   //货架号
    private String whClass;   //仓库类别
    private String whNote;    //备注
    private String convalue;  //查询输入值
    private String condition; //查询条件

    public int getWhId() {
        return whId;
    }

    public void setWhId(int whId) {
        this.whId = whId;
    }

    public String getWhAdress() {
        return whAdress;
    }

    public void setWhAdress(String whAdress) {
        this.whAdress = whAdress;
    }

    public String getWhArea() {
        return whArea;
    }

    public void setWhArea(String whArea) {
        this.whArea = whArea;
    }

    public String getShelves() {
        return shelves;
    }

    public void setShelves(String shelves) {
        this.shelves = shelves;
    }

    public String getWhClass() {
        return whClass;
    }

    public void setWhClass(String whClass) {
        this.whClass = whClass;
    }

    public String getWhNote() {
        return whNote;
    }

    public void setWhNote(String whNote) {
        this.whNote = whNote;
    }

    public String getConvalue() {
        return convalue;
    }

    public void setConvalue(String convalue) {
        this.convalue = convalue;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getWhNum() {
        return whNum;
    }

    public void setWhNum(String whNum) {
        this.whNum = whNum;
    }

    @Override
    public String toString() {
        return "WareHouse{" +
                "whId=" + whId +
                ", whNum='" + whNum + '\'' +
                ", whAdress='" + whAdress + '\'' +
                ", whArea='" + whArea + '\'' +
                ", shelves='" + shelves + '\'' +
                ", whClass='" + whClass + '\'' +
                ", whNote='" + whNote + '\'' +
                ", convalue='" + convalue + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}

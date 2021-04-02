package com.MySystem.pojo;

public class OutGoods extends MyPage {
    private int ogId;         //编号
    private String ogNum;     //出库商品编号
    private String ogName;   //出库商品名称
    private String ogClass;   //出库商品类别
    private Double ogPrice;   //出库商品单价
    private int ogCount;      //出库商品数量
    private Double ogTotal;   //出库商品总价

    private String ogTime;    //出库时间
    private String ogPerson;  //出库人
    private String ogSupply;  //供货商

    private String convalue;  //查询输入值
    private String condition; //查询条件

    public String getOgName() {
        return ogName;
    }

    public void setOgName(String ogName) {
        this.ogName = ogName;
    }

    public int getOgId() {
        return ogId;
    }

    public void setOgId(int ogId) {
        this.ogId = ogId;
    }

    public String getOgNum() {
        return ogNum;
    }

    public void setOgNum(String ogNum) {
        this.ogNum = ogNum;
    }

    public String getOgClass() {
        return ogClass;
    }

    public void setOgClass(String ogClass) {
        this.ogClass = ogClass;
    }

    public Double getOgPrice() {
        return ogPrice;
    }

    public void setOgPrice(Double ogPrice) {
        this.ogPrice = ogPrice;
    }

    public int getOgCount() {
        return ogCount;
    }

    public void setOgCount(int ogCount) {
        this.ogCount = ogCount;
    }

    public Double getOgTotal() {
        return ogTotal;
    }

    public void setOgTotal(Double ogTotal) {
        this.ogTotal = ogTotal;
    }

    public String getOgTime() {
        return ogTime;
    }

    public void setOgTime(String ogTime) {
        this.ogTime = ogTime;
    }

    public String getOgPerson() {
        return ogPerson;
    }

    public void setOgPerson(String ogPerson) {
        this.ogPerson = ogPerson;
    }

    public String getOgSupply() {
        return ogSupply;
    }

    public void setOgSupply(String ogSupply) {
        this.ogSupply = ogSupply;
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

    @Override
    public String toString() {
        return "OutGoods{" +
                "ogId=" + ogId +
                ", ogNum='" + ogNum + '\'' +
                ", ogName='" + ogName + '\'' +
                ", ogClass='" + ogClass + '\'' +
                ", ogPrice=" + ogPrice +
                ", ogCount=" + ogCount +
                ", ogTotal=" + ogTotal +
                ", ogTime='" + ogTime + '\'' +
                ", ogPerson='" + ogPerson + '\'' +
                ", ogSupply='" + ogSupply + '\'' +
                ", convalue='" + convalue + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}

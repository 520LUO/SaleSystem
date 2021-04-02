package com.MySystem.pojo;

public class IncomeGoods extends MyPage {
    private int igId;         //编号
    private String igNum;     //入库商品编号
    private String igName;   //入库商品名称
    private String igClass;   //入库商品类别
    private Double igPrice;   //入库商品单价
    private int igCount;      //入库商品数量
    private Double igTotal;   //入库商品总价

    private String igTime;    //入库时间
    private String igPerson;  //入库人
    private String igSupply;  //供货商

    private String convalue;  //查询输入值
    private String condition; //查询条件

    public String getIgName() {
        return igName;
    }

    public void setIgName(String igName) {
        this.igName = igName;
    }

    public int getIgId() {
        return igId;
    }

    public void setIgId(int igId) {
        this.igId = igId;
    }

    public String getIgNum() {
        return igNum;
    }

    public void setIgNum(String igNum) {
        this.igNum = igNum;
    }

    public String getIgClass() {
        return igClass;
    }

    public void setIgClass(String igClass) {
        this.igClass = igClass;
    }

    public Double getIgPrice() {
        return igPrice;
    }

    public void setIgPrice(Double igPrice) {
        this.igPrice = igPrice;
    }

    public int getIgCount() {
        return igCount;
    }

    public void setIgCount(int igCount) {
        this.igCount = igCount;
    }

    public Double getIgTotal() {
        return igTotal;
    }

    public void setIgTotal(Double igTotal) {
        this.igTotal = igTotal;
    }

    public String getIgTime() {
        return igTime;
    }

    public void setIgTime(String igTime) {
        this.igTime = igTime;
    }

    public String getIgPerson() {
        return igPerson;
    }

    public void setIgPerson(String igPerson) {
        this.igPerson = igPerson;
    }

    public String getIgSupply() {
        return igSupply;
    }

    public void setIgSupply(String igSupply) {
        this.igSupply = igSupply;
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
        return "IncomeGoods{" +
                "igId=" + igId +
                ", igNum='" + igNum + '\'' +
                ", igName='" + igName + '\'' +
                ", igClass='" + igClass + '\'' +
                ", igPrice=" + igPrice +
                ", igCount=" + igCount +
                ", igTotal=" + igTotal +
                ", igTime='" + igTime + '\'' +
                ", igPerson='" + igPerson + '\'' +
                ", igSupply='" + igSupply + '\'' +
                ", convalue='" + convalue + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}

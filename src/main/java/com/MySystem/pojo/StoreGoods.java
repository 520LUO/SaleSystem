package com.MySystem.pojo;

public class StoreGoods extends MyPage{
    private int sgId;         //编号
    private String sgNum;     //库存商品编号
    private String sgName;   //库存商品名称
    private String sgClass;   //库存商品类别
    private Double sgPrice;   //库存商品单价
    private int sgCount;      //库存商品数量
    private Double sgTotal;   //库存商品总价
    private String sgTime;    //入库时间
    private String sgSupply;  //供货商

    private String convalue;  //查询输入值
    private String condition; //查询条件

    public String getSgName() {
        return sgName;
    }

    public void setSgName(String sgName) {
        this.sgName = sgName;
    }

    public int getSgId() {
        return sgId;
    }

    public void setSgId(int sgId) {
        this.sgId = sgId;
    }

    public String getSgNum() {
        return sgNum;
    }

    public void setSgNum(String sgNum) {
        this.sgNum = sgNum;
    }

    public String getSgClass() {
        return sgClass;
    }

    public void setSgClass(String sgClass) {
        this.sgClass = sgClass;
    }

    public Double getSgPrice() {
        return sgPrice;
    }

    public void setSgPrice(Double sgPrice) {
        this.sgPrice = sgPrice;
    }

    public int getSgCount() {
        return sgCount;
    }

    public void setSgCount(int sgCount) {
        this.sgCount = sgCount;
    }

    public Double getSgTotal() {
        return sgTotal;
    }

    public void setSgTotal(Double sgTotal) {
        this.sgTotal = sgTotal;
    }

    public String getSgTime() {
        return sgTime;
    }

    public void setSgTime(String sgTime) {
        this.sgTime = sgTime;
    }

    public String getSgSupply() {
        return sgSupply;
    }

    public void setSgSupply(String sgSupply) {
        this.sgSupply = sgSupply;
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
        return "StoreGoods{" +
                "sgId=" + sgId +
                ", sgNum='" + sgNum + '\'' +
                ", sgName='" + sgName + '\'' +
                ", sgClass='" + sgClass + '\'' +
                ", sgPrice=" + sgPrice +
                ", sgCount=" + sgCount +
                ", sgTotal=" + sgTotal +
                ", sgTime='" + sgTime + '\'' +
                ", sgSupply='" + sgSupply + '\'' +
                ", convalue='" + convalue + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}

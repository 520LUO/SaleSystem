package com.MySystem.pojo;

public class SaleRecord  {
    private int saleId;
    private String saleNum;     //商品编号
    private String saleName;    //商品名称
    private int saleCount;
    private Double salePrice;
    private Double saleTotal;
    private String saleTime;    //卖出日期
    private String buyTel;      //买家联系电话
    private String buyEmail;    //买家联系邮箱
    private String storeArea;   //仓库所在地址
    private String saleArea;    //销售地址

    //定义页数，初始为1
    private int page=1;
    //每页数据条数
    private int row=6;

    private String convalue;    //查询输入值
    private String condition;   //查询条件

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public String getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(String saleNum) {
        this.saleNum = saleNum;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Double getSaleTotal() {
        return saleTotal;
    }

    public void setSaleTotal(Double saleTotal) {
        this.saleTotal = saleTotal;
    }

    public String getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(String saleTime) {
        this.saleTime = saleTime;
    }

    public String getBuyTel() {
        return buyTel;
    }

    public void setBuyTel(String buyTel) {
        this.buyTel = buyTel;
    }

    public String getBuyEmail() {
        return buyEmail;
    }

    public void setBuyEmail(String buyEmail) {
        this.buyEmail = buyEmail;
    }

    public String getStoreArea() {
        return storeArea;
    }

    public void setStoreArea(String storeArea) {
        this.storeArea = storeArea;
    }

    public String getSaleArea() {
        return saleArea;
    }

    public void setSaleArea(String saleArea) {
        this.saleArea = saleArea;
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
        return "SaleRecord{" +
                "saleId=" + saleId +
                ", saleNum='" + saleNum + '\'' +
                ", saleName='" + saleName + '\'' +
                ", saleCount=" + saleCount +
                ", salePrice=" + salePrice +
                ", saleTotal=" + saleTotal +
                ", saleTime='" + saleTime + '\'' +
                ", buyTel='" + buyTel + '\'' +
                ", buyEmail='" + buyEmail + '\'' +
                ", storeArea='" + storeArea + '\'' +
                ", saleArea='" + saleArea + '\'' +
                ", convalue='" + convalue + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}

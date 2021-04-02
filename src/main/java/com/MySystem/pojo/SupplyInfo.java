package com.MySystem.pojo;

public class SupplyInfo extends MyPage {
    private int supId;//供货商编号
    private String supAgent;  //供货商代理人
    private String sex;       //性别
    private String supTel;    //联系电话
    private String supEmail;  //邮箱地址
    private String certName;  //证件名称
    private String certUrl;   //证件图片
    private String certNum;   //证件号码
    private String startTime; //签发时间
    private String validTime; //有效期限
    private String convalue;  //查询输入值
    private String condition; //查询条件

    public int getSupId() {
        return supId;
    }

    public void setSupId(int supId) {
        this.supId = supId;
    }

    public String getSupAgent() {
        return supAgent;
    }

    public void setSupAgent(String supAgent) {
        this.supAgent = supAgent;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSupTel() {
        return supTel;
    }

    public void setSupTel(String supTel) {
        this.supTel = supTel;
    }

    public String getSupEmail() {
        return supEmail;
    }

    public void setSupEmail(String supEmail) {
        this.supEmail = supEmail;
    }

    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName;
    }

    public String getCertUrl() {
        return certUrl;
    }

    public void setCertUrl(String certUrl) {
        this.certUrl = certUrl;
    }

    public String getCertNum() {
        return certNum;
    }

    public void setCertNum(String certNum) {
        this.certNum = certNum;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
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
        return "SupplyInfo{" +
                "supId=" + supId +
                ", supAgent='" + supAgent + '\'' +
                ", sex='" + sex + '\'' +
                ", supTel='" + supTel + '\'' +
                ", supEmail='" + supEmail + '\'' +
                ", certName='" + certName + '\'' +
                ", certUrl='" + certUrl + '\'' +
                ", certNum='" + certNum + '\'' +
                ", startTime='" + startTime + '\'' +
                ", validTime='" + validTime + '\'' +
                '}';
    }
}

package com.MySystem.pojo;

//统计数据对象
public class Analysis {

    private String name;
    private int count;
    private double money;
    private String time;
    //查询条件
    private String convalue;
    private String condition;
    private String type;//出库还是入库

    public Analysis() {
    }


    public Analysis(int count, double money, String time) {
        this.count = count;
        this.money = money;
        this.time = time;
    }

    public Analysis(String name, int count, double money) {
        this.name = name;
        this.count = count;
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Analysis{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", money=" + money +
                ", time='" + time + '\'' +
                ", convalue='" + convalue + '\'' +
                ", condition='" + condition + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

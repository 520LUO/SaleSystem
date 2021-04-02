package com.MySystem.pojo;

public class AdressInfo {
      private String name;  //地址名称
      private Double log;   //地址经度
      private Double lat;   //地址纬度

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLog() {
        return log;
    }

    public void setLog(Double log) {
        this.log = log;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "AdressInfo{" +
                "name='" + name + '\'' +
                ", log=" + log +
                ", lat=" + lat +
                '}';
    }
}

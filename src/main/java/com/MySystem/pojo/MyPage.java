package com.MySystem.pojo;
//描述分页对象
public class MyPage {
    //定义页数，初始为1
    private int page=1;
    //每页数据条数
    private int row=6;

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
}

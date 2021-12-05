package com.example.demo.struct.response.request;

import java.sql.Timestamp;

public class R_req_mine_issue {
    public Integer id;
    public String name;
    public String username;
    public Integer type;
    public String title;
    public String description;
    public Integer headcount;
    public Timestamp createTime;
    public Timestamp updateTime;
    public Timestamp endTime;
    //    public Blob image;
    public Double commissionFee;
    public Integer state;

    public R_req_mine_issue(Integer x1, String x2, String x3,
                      Integer x4, String x5, String x6,
                      Integer x7, Timestamp x8,Timestamp x9,
                      Timestamp x10, Double x11, Integer x12){
        this.id = x1;
        this.name = x2;
        this.username = x3;
        this.type = x4;
        this.title = x5;
        this.description = x6;
        this.headcount = x7;
        this.createTime = x8;
        this.updateTime = x9;
        this.endTime = x10;
        this.commissionFee = x11;
        this.state = x12;
    }

    public R_req_mine_issue(){}
}

package com.example.demo.struct.response.request;

import com.example.demo.entity.Request;

import java.sql.Timestamp;

public class R_query_issue {
    public Integer id;
    public String name;
    public String username;
    public Integer type;
    public String regCommunity;
    public String title;
    public String description;
    public Integer headcount;
    public Timestamp createTime;
    public Timestamp updateTime;
    public Timestamp endTime;
    //    public Blob image;
    public Double commissionFee;
    public Integer state;

    public R_query_issue(){}

    public R_query_issue(Integer id2, String name2, String username2,
                   Integer type2, String community2, String title2,
                   String description2, Integer headcount2, Timestamp create_time2,
                   Timestamp update_time2, Timestamp end_time2, Double commission_fee2,
                   Integer state2){
        this.id = id2;
        this.name = name2;
        this.username = username2;
        this.type = type2;
        this.regCommunity = community2;
        this.title = title2;
        this.description = description2;
        this.headcount = headcount2;
        this.createTime = create_time2;
        this.updateTime = update_time2;
        this.endTime = end_time2;
        this.commissionFee = commission_fee2;
        this.state = state2;
    }

    public R_query_issue(Request request, String community){
        this.id = request.id;
        this.name = request.name;
        this.username = request.username;
        this.type = request.type;
        this.regCommunity = community;
        this.title = request.title;
        this.description = request.description;
        this.headcount = request.headcount;
        this.createTime = request.create_time;
        this.updateTime = request.update_time;
        this.endTime = request.end_time;
        this.commissionFee = request.commission_fee;
        this.state = request.state;
    }
}

package com.example.demo.struct.response.user;

import com.example.demo.struct.Status;

import java.sql.Timestamp;

public class R_info {
    public Status status = new Status();
    public String username;
    public Integer type;
    public String name;
    public Integer certificateType;
    public String certificateNum;
    public String phoneNum;
    public Integer userLevel;
    public String introduction;
    public String regCity;
    public String regCommunity;
    public Timestamp regTime;
    public Timestamp updateTime;

    public R_info(){
        status.msg = "R_info未初始化";
    }

}

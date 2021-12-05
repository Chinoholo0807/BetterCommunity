package com.example.demo.struct.posttype.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class P_register {
    public String username;
    public String password;
    public String name;
    public Integer certificateType;
    public String certificateNum;
    public String phoneNum;
    public String introduction;
    public String regCity;
    public String regCommunity;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
    public Timestamp regTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
    public Timestamp updateTime;

    public int check(){
        int check = 1;
        if(username.isEmpty()
                || password == null
                || name == null
                || certificateType > 2
                || certificateType < 0
                || certificateNum == null
                || phoneNum == null
                || regCity == null
                || regCommunity == null
                || regTime == null
                || updateTime == null)check = 0;

        return check;
    }
}

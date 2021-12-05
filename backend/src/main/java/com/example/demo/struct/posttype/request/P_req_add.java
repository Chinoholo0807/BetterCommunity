package com.example.demo.struct.posttype.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Blob;
import java.sql.Timestamp;

public class P_req_add {
    public Integer type;
    public String title;
    public String description;
    public Integer headcount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
    public Timestamp createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
    public Timestamp endTime;
//    public Blob image;
    public  Double commissionFee;

    public int check(){
        int check = 1;
        if(type == null
                || type.intValue() > 4
                || title == null
                || description == null
                || headcount.intValue() < 0
                || createTime == null
                || endTime == null
                || commissionFee == null)check = 0;

        return check;
    }
}

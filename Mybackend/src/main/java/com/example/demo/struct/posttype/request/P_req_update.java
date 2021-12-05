package com.example.demo.struct.posttype.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Blob;
import java.sql.Timestamp;

public class P_req_update {
    public Integer id;
    public String name;
    public String username;
    public Integer type;
    public String title;
    public String description;
    public Integer headcount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
    public Timestamp endTime;
//    public Blob image;
    public Double commissionFee;

    public int check(){
        int check = 1;
        if(id == null
                || name == null
                || username == null
                || type == null
                || title == null)check = 0;

        return check;
    }
}

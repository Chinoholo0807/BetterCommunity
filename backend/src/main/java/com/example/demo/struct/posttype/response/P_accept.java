package com.example.demo.struct.posttype.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class P_accept {
    public Integer requestId;
    public String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
    public Timestamp createTime;

    public int check(){
        int check = 1;
        if(requestId == null
                || createTime == null)check = 0;
        System.out.println(requestId);
        System.out.println(createTime);

        return check;
    }
}

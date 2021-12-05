package com.example.demo.struct.posttype.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class P_update {
    public Integer id;
    public String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
    public Timestamp updateTime;
}

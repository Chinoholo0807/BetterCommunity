package com.example.demo.struct.response.response;

import com.example.demo.struct.Status;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class R_query {
    public Status status = new Status();
    public List<R_query_issue> resps = new ArrayList<>(0);

    public R_query(){
        status.code = 400;
        status.msg = "R_query未初始化";
    }


}

package com.example.demo.struct.response.response;

import com.example.demo.struct.Status;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class R_mine {
    public Status status = new Status();
    public List<R_mine_issue> resps = new ArrayList<>(0);

    public R_mine(){
        status.code = 400;
        status.msg = "R_mine未初始化";
    }

}

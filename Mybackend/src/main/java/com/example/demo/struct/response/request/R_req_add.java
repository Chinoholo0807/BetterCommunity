package com.example.demo.struct.response.request;

import com.example.demo.struct.Status;

public class R_req_add {
    public Status status = new Status();
    public Integer id;
    public R_req_add(){
        status.msg = "R_req_add未初始化";
        id = -1;
    }
}

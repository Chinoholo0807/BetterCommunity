package com.example.demo.struct.response.request;

import com.example.demo.struct.Status;

public class R_req_update {
    public Status status = new Status();
    public R_req_update(){
        status.msg = "R_req_update未初始化";
    }
}

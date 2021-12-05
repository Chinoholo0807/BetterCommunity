package com.example.demo.struct.response.request;

import com.example.demo.struct.Status;

public class R_req_delete {
    public Status status = new Status();

    public R_req_delete(){
        status.msg = "R_req_delete未初始化";
    }
}

package com.example.demo.struct.response.response;

import com.example.demo.struct.Status;

public class R_update {
    public Status status = new Status();
    public R_update(){
        status.msg = "resp_update未初始化";
    }
}

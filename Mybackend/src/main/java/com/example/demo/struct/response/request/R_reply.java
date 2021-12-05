package com.example.demo.struct.response.request;

import com.example.demo.struct.Status;

public class R_reply {
    public Status status = new Status();

    public R_reply(){
        status.msg = "R_reply未初始化";
        status.code = 400;
    }
}

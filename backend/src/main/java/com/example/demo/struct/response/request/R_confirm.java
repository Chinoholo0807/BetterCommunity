package com.example.demo.struct.response.request;

import com.example.demo.struct.Status;

public class R_confirm {
    public Status status = new Status();

    public R_confirm(){
        status.msg = "R_confirm未初始化";
        status.code = 400;
    }
}

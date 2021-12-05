package com.example.demo.struct.response.user;

import com.example.demo.struct.Status;

public class R_update {
    public Status status = new Status();
    public R_update(){
        this.status.msg = "R_update未初始化";
    }
}

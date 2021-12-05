package com.example.demo.struct.response.response;

import com.example.demo.struct.Status;

public class R_delete {
    public Status status = new Status();
    public R_delete(){
        status.msg = "resp_delete未初始化";
    }
}

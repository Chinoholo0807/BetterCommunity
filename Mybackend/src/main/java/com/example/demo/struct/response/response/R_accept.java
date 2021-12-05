package com.example.demo.struct.response.response;

import com.example.demo.struct.Status;

public class R_accept {
    public Status status = new Status();
    public Integer id;
    public R_accept(){
        id = -1;
        status.msg = "resp_accept未初始化";
    }
}

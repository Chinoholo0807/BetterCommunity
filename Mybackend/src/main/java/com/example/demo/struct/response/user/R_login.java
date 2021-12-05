package com.example.demo.struct.response.user;

import com.example.demo.struct.Status;

public class R_login {
    public Status status = new Status();
    public String token;
    public R_login(){
        status.msg = "r_login未初始化";
        token = "token未初始化";
    }
}

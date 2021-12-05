package com.example.demo.struct.posttype.request;

public class P_confirm {
    public Integer requestId;

    public int check(){
        int check = 1;
        if(requestId == null)check = 0;
        return check;
    }
}

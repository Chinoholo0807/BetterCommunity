package com.example.demo.struct.posttype.request;

public class P_reply {
    public Integer requestId;
    public Integer responseId;
    public Integer operate;

    public int check(){
        int check = 1;
        if(requestId == null
                || responseId == null
                || operate == null)check = 0;

        return check;
    }
}

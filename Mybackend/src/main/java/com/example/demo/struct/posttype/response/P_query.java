package com.example.demo.struct.posttype.response;

public class P_query {
    public Integer id;
    public Integer requestId;

    public int check(){
        int check = 1;
        if(id == null || requestId == null)check = 0;

        return check;
    }
}

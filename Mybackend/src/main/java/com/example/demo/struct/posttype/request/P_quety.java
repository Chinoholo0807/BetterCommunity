package com.example.demo.struct.posttype.request;

public class P_quety {
    public Integer type;
    public String title;
    public String regCommunity;
    public Integer id;

    public int check(){
        int check = 1;
        if(type.intValue() < 0
                || type.intValue() > 5)check = 0;

        return check;
    }
}

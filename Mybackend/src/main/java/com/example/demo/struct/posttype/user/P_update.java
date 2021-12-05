package com.example.demo.struct.posttype.user;

import java.sql.Timestamp;

public class P_update {
    public String phoneNum;
    public String introduction;
    public String password;
    public Timestamp updateTime;

    public int check(){
        int check = 1;
        if(phoneNum == null
                || password == null)check = 0;

        return check;
    }

}

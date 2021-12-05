package com.example.demo.struct.posttype.user;

public class P_login {
    public String username;
    public String password;
    public int check(){
        int check = 1;
        if(username == null
                || password == null)check = 0;
        return check;
    }

}

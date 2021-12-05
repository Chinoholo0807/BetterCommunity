package com.example.demo.struct;

public class Loginrequest {
    public Status status;
    public String username;
    public String password;
    public Loginrequest(int a){
        username = "123";
        password = "321";
        status = new Status();
    };
}

package com.example.demo.society.method;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.User;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserInfoTest {
    @Autowired
    private Userinterface userinterface;

    @Test
    void findAll(){
        System.out.println("\n\n123456\n\n123456");
        System.out.println(userinterface.findAll().size());
        User temp1 = userinterface.findAll().get(0);
        System.out.println(temp1.username);
        System.out.println(temp1);
        System.out.println(userinterface.findAll());
        System.out.println("123456\n\n123456");
    }

}
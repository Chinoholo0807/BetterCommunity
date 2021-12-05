package com.example.demo.entity;

//请求信息类
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "requests")
public class Request {
    @Id
    public Integer id;
    @Column(name = "user_id")
    public Integer user_id;
    @Column(name = "username")
    public String username;
    @Column(name = "name")
    public String name;
    @Column(name = "type")
    public Integer type;
    @Column(name = "title")
    public String title;
    @Column(name = "description")
    public String description;
    @Column(name = "headcount")
    public Integer headcount;
    @Column(name = "create_time")
    public Timestamp create_time;
    @Column(name = "update_time")
    public Timestamp update_time;
    @Column(name = "end_time")
    public Timestamp end_time;
    @Column(name = "image")
    public Blob image;
    @Column(name = "state")
    public Integer state;
    @Column(name = "commission_fee")
    public Double commission_fee;



}

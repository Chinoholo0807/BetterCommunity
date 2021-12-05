package com.example.demo.entity.runuse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;

@Entity     //表示这是一个实体类
@Table(name = "login_user")   //对应数据库中的user表
public class Login_user {
    @Id
    public String username;
    @Column(name = "state")
    public Integer state;
    @Column(name = "token")
    public String token;
}

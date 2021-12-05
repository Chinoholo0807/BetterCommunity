//与数据库中数据对应的实体类

package com.example.demo.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity     //表示这是一个实体类
@Table(name = "users")   //对应数据库中的user表
public class User /*implements Serializable*/ {
    //private static final long serialVersionUID = 1L;

    @Id                                 //表示主键
    public Integer id;
    @Column(name = "username")
    public String username;
    @Column(name = "password")          //表明一个属性
    public String password;
    @Column(name = "type")
    public Integer type;
    @Column(name = "name")
    public String name;
    @Column(name = "certificate_type")
    public Integer certificate_type;
    @Column(name = "certificate_num")
    public String certificate_num;
    @Column(name = "phone_num")
    public String phone_num;
    @Column(name = "user_level")
    public Integer user_level;
    @Column(name = "introduction")
    public String introduction;
    @Column(name = "reg_city")
    public String reg_city;
    @Column(name = "reg_community")
    public String reg_community;
    @Column(name = "reg_time")
    public Timestamp reg_time;
    @Column(name = "update_time")
    public Timestamp update_time;

//    public User(Integer id2, String username2, String password2,
//                Integer type2, String name2, Integer certificate_type2,
//                String certificate_num2, String phone_num2, Integer user_level2,
//                String introduction2, String reg_city2, String reg_community2,
//                Timestamp reg_time2, Timestamp update_time2){
//        this.id = id2;
//        this.username = username2;
//        this.password = password2;
//        this.type = type2;
//        this.name = name2;
//        this.certificate_type = certificate_type2;
//        this.certificate_num = certificate_num2;
//        this.phone_num = phone_num2;
//        this.user_level = user_level2;
//        this.introduction = introduction2;
//        this.reg_city = reg_city2;
//        this.reg_community = reg_community2;
//        this.reg_time = reg_time2;
//        this.update_time = update_time2;
//    }


    public String getUserName(){
        return this.username;
    }
    public void setUserName(String x){
        this.username = x;
    }

    public String getPassword(){
        return this.password;
    }
    public void setPassword(String x){
        this.password = x;
    }




}

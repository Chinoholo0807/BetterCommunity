package com.example.demo.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity     //表示这是一个实体类
@Table(name = "agency_summaries")   //对应数据库中的user表
public class Summary {
    @Id                                 //表示主键
    public Integer id;
    @Column(name = "city")
    public String city;
    @Column(name = "community")
    public String community;
    @Column(name = "type")
    public Integer type;
    @Column(name = "achieve_cnt")
    public Integer achieve_cnt;
    @Column(name = "total_income")
    public Double total_income;
}

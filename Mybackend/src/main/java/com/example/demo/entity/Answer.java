package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;


@Entity
@Table(name = "responses")
public class Answer {
    @Id
    public Integer id;
    @Column(name = "request_id")
    public Integer request_id;
    @Column(name = "user_id")
    public Integer user_id;
    @Column(name = "description")
    public String description;
    @Column(name = "create_time")
    public Timestamp create_time;
    @Column(name = "update_time")
    public Timestamp update_time;
    @Column(name = "state")
    public Integer state;

}

package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "detail_lists")
public class Success {
    @Id
    public Integer id;
    @Column(name = "request_id")
    public Integer request_id;
    @Column(name = "req_user_id")
    public Integer req_user_id;
    @Column(name = "response_id")
    public Integer response_id;
    @Column(name = "resp_user_id")
    public Integer resp_user_id;
    @Column(name = "achieve_time")
    public Timestamp achieve_time;
    @Column(name = "agency_fee")
    public Double agency_fee;
}

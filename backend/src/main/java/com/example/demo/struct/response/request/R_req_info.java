package com.example.demo.struct.response.request;

import com.example.demo.struct.Status;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.sql.Timestamp;


//@Data
public class R_req_info implements Serializable {
    public Status status = new Status();
    public Integer id;
    public String name;
    public String username;
    public Integer type;
    public String regCommunity;
    public String title;
    public String description;
    public Integer headcount;
    public Timestamp createTime;
    public Timestamp updateTime;
    public Timestamp endTime;
//    public Blob image;
    public Double commissionFee;
    public Integer state;

    public R_req_info(Integer id2, Integer user_id2, Integer type2,
                      String title2, String description2, Integer headcount2,
                      Timestamp create_time2, Timestamp update_time2, Timestamp end_time2,
                      Double commission_fee2, Integer state2){
        this.id = id2;
//        this.userName = user_id2;
        this.type = type2;
        this.title = title2;
        this.description = description2;
        this.headcount = headcount2;
        this.createTime = create_time2;
        this.updateTime = update_time2;
        this.endTime = end_time2;
        this.commissionFee = commission_fee2;
        this.state = state2;
    }

    public R_req_info(){
    };
}

package com.example.demo.struct.response.response;

import java.sql.Timestamp;

public class R_mine_issue {
    public Integer id;
    public Integer requestId;
    public Timestamp createTime;
    public Timestamp updateTime;
    public String description;
    public Integer state;
    public String name;
    public String username;

    public R_mine_issue(){}

    public R_mine_issue(Integer id2, Integer requestId2, Timestamp createTime2,
                  Timestamp updateTime2, String description2, Integer state2,
                  String name2, String username2){
        this.id = id2;
        this.requestId = requestId2;
        this.createTime = createTime2;
        this.updateTime = updateTime2;
        this.description = description2;
        this.state = state2;
        this.name = name2;
        this.username = username2;
    }
}

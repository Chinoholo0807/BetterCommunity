package com.example.demo.struct.response.request;

import com.example.demo.struct.Status;
import lombok.Data;

import java.sql.Blob;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class R_req_mine {
    public Status status = new Status();
    public List<R_req_mine_issue> issues= new ArrayList<>(0);

    public R_req_mine(){
    };


}

package com.example.demo.struct;

import org.apache.logging.log4j.message.Message;

public class Response {
    public Status status = new Status();
//    public Message message;
    public Response(){
        status.msg = "Response未初始化";
    }

}

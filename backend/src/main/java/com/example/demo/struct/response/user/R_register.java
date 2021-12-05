package com.example.demo.struct.response.user;

import com.example.demo.struct.Status;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToUrl;

public class R_register {
    public Status status = new Status();
    public R_register(){
        this.status.msg = "R_register未初始化";
    }
}

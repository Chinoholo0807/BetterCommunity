package com.example.demo.controllor;


import com.example.demo.entity.runuse.My_transfer;
import com.example.demo.entity.runuse.TokenUtil;
import com.example.demo.society.method.Answerinterface;
import com.example.demo.society.method.Userinterface;
import com.example.demo.struct.posttype.response.P_accept;
import com.example.demo.struct.posttype.response.P_delete;
import com.example.demo.struct.posttype.response.P_update;
import com.example.demo.struct.response.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/resp")
public class Answerhandler {
    @Autowired
    public Answerinterface answerinterface;

    @Autowired
    public Userinterface userinterface;

    @GetMapping("/query")
    public R_query query(@RequestParam(value = "id", required = false) Integer id,
                               @RequestParam(value = "requestId", required = false) Integer requestId){
        R_query r_query = new R_query();
        r_query.status.code = 400;
        if(id != null){
            System.out.println("通过响应id查找");
            try {
                List<Object[]> ret = answerinterface.findQueryById(id);
                R_query_issue r_query_issue = new R_query_issue();
                List<R_query_issue> r_query_issueList = My_transfer.castEntity(ret, R_query_issue.class, r_query_issue);
                for(int i = 0; i < r_query_issueList.size(); i++)
                    r_query.resps.add(r_query_issueList.get(i));
                r_query.status.code = 200;
                r_query.status.msg = "成功";
            }catch (Exception e){
                System.out.println("查询错误");
                r_query.status.msg = "通过响应id查找失败";
            }
        }else if(requestId != null){
            System.out.println("通过请求id，查找所有响应该请求的响应信息");
            try {
                List<Object[]> ret = answerinterface.findQueryByRequestId(requestId);
                R_query_issue r_query_issue = new R_query_issue();
                List<R_query_issue> r_query_issueList = My_transfer.castEntity(ret, R_query_issue.class, r_query_issue);
                for(int i = 0; i < r_query_issueList.size(); i++)
                    r_query.resps.add(r_query_issueList.get(i));
                r_query.status.code = 200;
                r_query.status.msg = "成功";
            }catch (Exception e){
                System.out.println("查询错误");
                r_query.status.msg = "通过请求id查找失败";
            }
        }else {
            try {
                System.out.println("id、requestId均不存在,返回所有响应信息");
                List<Object[]> ret = answerinterface.findQuery();
                R_query_issue r_query_issue = new R_query_issue();
                List<R_query_issue> r_query_issueList = My_transfer.castEntity(ret, R_query_issue.class, r_query_issue);
                for(int i = 0; i < r_query_issueList.size(); i++)
                    r_query.resps.add(r_query_issueList.get(i));
                r_query.status.code = 200;
                r_query.status.msg = "成功";
            }catch (Exception e){
                System.out.println("查询错误");
                r_query.status.msg = "查找所有信息失败";
            }
        }
        return r_query;
    }

    @PostMapping("/accept")
    public R_accept accept(@RequestHeader("Authorization") String token, @RequestBody P_accept input){
        R_accept r_accept = new R_accept();
        r_accept.status.msg = "响应不成功";
        r_accept.status.code = 400;
        if(input.check() == 0){
            r_accept.status.msg = "参数错误";
            return r_accept;
        }
        try {
            System.out.println("向数据库中增加响应(create_time:" + input.createTime.toString() + ")");
            String username = TokenUtil.decodeToken(token);
            Integer find_user_id = userinterface.findIdByUsername(username);
            answerinterface
                    .Add(input.requestId, find_user_id, input.description,
                            My_transfer.cutTimeStamp(input.createTime.toString()));
            try {
                System.out.println("增加响应成功，查找id值");
                r_accept.id = answerinterface.findIdByCreateTime(Timestamp.valueOf(
                                                                                    My_transfer.cutTimeStamp(
                                                                                    input.createTime.toString())));
                r_accept.status.code = 200;
                r_accept.status.msg = "响应成功";
            }catch (Exception e){
                System.out.println("查找id失败:" + e.getMessage());
            }
        }catch (Exception e){
            System.out.println("增加失败");
            r_accept.status.msg = "响应数据库写入失败";
        }
        return r_accept;
    }

    @GetMapping("/mine")
    public R_mine mine(@RequestHeader("Authorization") String token){
        String username = TokenUtil.decodeToken(token);
        R_mine r_mine = new R_mine();
        r_mine.status.code = 400;
        try {
            System.out.println("通过username查找该用户的所有响应信息");
            List<Object[]> ret = answerinterface.findMineByUsername(username);
            R_mine_issue r_mine_issue = new R_mine_issue();
            List<R_mine_issue> r_mine_issues = My_transfer.castEntity(ret, R_mine_issue.class, r_mine_issue);
            for(int i = 0; i < r_mine_issues.size(); i++)
                r_mine.resps.add(r_mine_issues.get(i));
            r_mine.status.code = 200;
            r_mine.status.msg = "成功";
        }catch (Exception e){
            System.out.println("查找失败");
            r_mine.status.code = 400;
            r_mine.status.msg = "查找失败";
        }
        return r_mine;
    }

    @PostMapping("/update")
    public R_update update(@RequestBody P_update input){
        R_update r_update = new R_update();
        r_update.status.code = 400;
        try {
            System.out.println("查找该请求对应的状态，看能不能被响应(update_time:" + input.updateTime.toString() + ")");
            Integer state = answerinterface.findStateById(input.id);
            if(state.equals(0)){
                try {
                    System.out.println("开始修改响应");
                    answerinterface.modifyById(input.id, input.description, My_transfer.cutTimeStamp(input.updateTime.toString()));
                    r_update.status.msg = "修改相应成功";
                    r_update.status.code = 200;
                }catch (Exception e){
                    System.out.println("修改响应失败:" + e.getMessage());
                    r_update.status.msg = "修改响应失败";
                }
            }else {
                System.out.println("该响应不能被修改");
                r_update.status.msg = "修改已接受/被拒绝的响应:" + state.toString();
            }
        }catch (Exception e){
            System.out.println("找不到该id的请求");
            r_update.status.msg = "找不到该id的i请求";
        }
        return r_update;
    }

    @PostMapping("/delete")
    public R_delete delete(@RequestBody P_delete input){
        R_delete r_delete = new R_delete();
        r_delete.status.code = 400;
        try {
            System.out.println("查找该请求对应的状态，看能不能被删除");
            Integer state = answerinterface.findStateById(input.id);
            if(state.equals(0)){
                try {
                    System.out.println("开始删除响应");
                    answerinterface.modifyStateById(input.id, 3);
                    r_delete.status.msg = "删除相应成功";
                    r_delete.status.code = 200;
                }catch (Exception e){
                    System.out.println("删除响应失败");
                    r_delete.status.msg = "删除响应失败";
                }
            }else {
                r_delete.status.msg = "删除已接受的响应:" + state.toString();
            }
        }catch (Exception e){
            System.out.println("找不到该id的请求");
            r_delete.status.msg = "找不到该id的i请求";
        }
        return r_delete;
    }


}

package com.example.demo.controllor;

import com.example.demo.entity.Request;
import com.example.demo.entity.User;
import com.example.demo.entity.runuse.My_transfer;
import com.example.demo.entity.runuse.TokenUtil;
import com.example.demo.society.method.Answerinterface;
import com.example.demo.society.method.Requestinterface;
import com.example.demo.society.method.Userinterface;
import com.example.demo.struct.posttype.request.*;
import com.example.demo.struct.response.request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/req")
public class Requesthandler {
    @Autowired
    public Requestinterface requestinterface;

    @Autowired
    public Answerinterface answerinterface;

    @Autowired
    public Userinterface userinterface;

    @GetMapping("/query")
    public R_query query(@RequestHeader("Authorization") String token,
                               @RequestParam(value = "type", required = false) Integer type,
                               @RequestParam(value = "title", required = false) String title,
                               @RequestParam(value = "regCommunity", required = false) String regCommunity,
                               @RequestParam(value = "id", required = false) Integer id){
        R_query r_query = new R_query();
        r_query.status.code = 400;
        try {
            System.out.println("获取query参数：type:" + type + "---title:" + title + "---community:" + regCommunity + "---id:" + id);
        }catch (Exception e){
            System.out.println("参数异常");
            r_query.status.code = 400;
            r_query.status.msg = "参数异常";
            return r_query;
        }

        try {
            System.out.println("尝试查找符合条件的委托");
            if(id == null || id.toString().isEmpty()){
                if((title == null || title.isEmpty()) && (regCommunity == null || regCommunity.isEmpty())){
                    System.out.println("->根据 type 查找委托");
                    if(type.equals(5)){
                        List<Object[]> ret = requestinterface.findQuery();
                        R_query_issue r_query_issue = new R_query_issue();
                        System.out.println("数量：" + ret.size());
                        List<R_query_issue> r_query_issueList = My_transfer.castEntity(ret, R_query_issue.class, r_query_issue);
                        for(int i = 0; i < r_query_issueList.size(); i++)
                            r_query.issues.add(r_query_issueList.get(i));
                    }else{
                        List<Object[]> ret = requestinterface.findQueryByType(type);
                        R_query_issue r_query_issue = new R_query_issue();
                        System.out.println("数量：" + ret.size());
                        List<R_query_issue> r_query_issueList = My_transfer.castEntity(ret, R_query_issue.class, r_query_issue);
                        for(int i = 0; i < r_query_issueList.size(); i++)
                            r_query.issues.add(r_query_issueList.get(i));
                    }

                }else if((title == null || title.isEmpty()) && !(regCommunity == null || regCommunity.isEmpty())){
                    System.out.println("->根据 type 和 community 查找委托");
                    if(type.equals(5)){
                        List<Object[]> ret = requestinterface.findQueryByCommunity("%"+regCommunity+"%");
                        R_query_issue r_query_issue = new R_query_issue();
                        System.out.println("数量：" + ret.size());
                        List<R_query_issue> r_query_issueList = My_transfer.castEntity(ret, R_query_issue.class, r_query_issue);
                        for(int i = 0; i < r_query_issueList.size(); i++)
                            r_query.issues.add(r_query_issueList.get(i));
                    }else {
                        List<Object[]> ret = requestinterface.findQueryByTypeCommunity(type, "%"+regCommunity+"%");
                        R_query_issue r_query_issue = new R_query_issue();
                        System.out.println("数量：" + ret.size());
                        List<R_query_issue> r_query_issueList = My_transfer.castEntity(ret, R_query_issue.class, r_query_issue);
                        for(int i = 0; i < r_query_issueList.size(); i++)
                            r_query.issues.add(r_query_issueList.get(i));
                    }

                }else if(!(title == null || title.isEmpty()) && (regCommunity == null || regCommunity.isEmpty())) {
                    System.out.println("->根据 type 和 title 查找委托");
                    if(type.equals(5)){
                        List<Object[]> ret = requestinterface.findQueryByTitle("%"+title+"%");
                        R_query_issue r_query_issue = new R_query_issue();
                        System.out.println("数量：" + ret.size());
                        List<R_query_issue> r_query_issueList = My_transfer.castEntity(ret, R_query_issue.class, r_query_issue);
                        for(int i = 0; i < r_query_issueList.size(); i++)
                            r_query.issues.add(r_query_issueList.get(i));
                    }else {
                        List<Object[]> ret = requestinterface.findQueryByTypeTitle(type, "%"+title+"%");
                        R_query_issue r_query_issue = new R_query_issue();
                        System.out.println("数量：" + ret.size());
                        List<R_query_issue> r_query_issueList = My_transfer.castEntity(ret, R_query_issue.class, r_query_issue);
                        for(int i = 0; i < r_query_issueList.size(); i++)
                            r_query.issues.add(r_query_issueList.get(i));
                    }
                }else if(!(title == null || title.isEmpty()) && !(regCommunity == null || regCommunity.isEmpty())){
                    System.out.println("->根据 type、title、community 查找委托");
                    if(type.equals(5)){
                        List<Object[]> ret = requestinterface.findQueryByTitleCommunity("%"+title+"%", "%"+regCommunity+"%");
                        R_query_issue r_query_issue = new R_query_issue();
                        System.out.println("数量：" + ret.size());
                        List<R_query_issue> r_query_issueList = My_transfer.castEntity(ret, R_query_issue.class, r_query_issue);
                        for(int i = 0; i < r_query_issueList.size(); i++)
                            r_query.issues.add(r_query_issueList.get(i));
                    }else {
                        List<Object[]> ret = requestinterface.findQueryByTypeTitleCommunity(type, "%"+title+"%", "%"+regCommunity+"%");
                        R_query_issue r_query_issue = new R_query_issue();
                        System.out.println("数量：" + ret.size());
                        List<R_query_issue> r_query_issueList = My_transfer.castEntity(ret, R_query_issue.class, r_query_issue);
                        for(int i = 0; i < r_query_issueList.size(); i++)
                            r_query.issues.add(r_query_issueList.get(i));
                    }
                }else {
                    System.out.println("----------------------不应该到这里！！！");
                    List<Object[]> ret;
                }
                r_query.status.code = 200;
                r_query.status.msg = "查找成功";
            }else {
                System.out.println("->根据 id 查找委托");
                Request temp_find = requestinterface.findAllById(id);
                String username = temp_find.username;
                String community = userinterface.findRegCommunityByUsername(username);
                R_query_issue r_query_issue = new R_query_issue(temp_find, community);
                r_query.issues.add(r_query_issue);
                r_query.status.code = 200;
                r_query.status.msg = "查找成功";
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("查找数据库失败:" + e.getMessage());
            r_query.status.code = 400;
            r_query.status.msg = "查找数据库失败";
        }
        return r_query;
    }

    @GetMapping("/mine")
    public R_req_mine mine(@RequestHeader("Authorization") String token){
        R_req_mine r_req_mine = new R_req_mine();
        r_req_mine.status.code = 400;
        String username = TokenUtil.decodeToken(token);
        try {
            System.out.println("尝试查找该用户的所有请求：username(" + username + ")");
            List<Object[]> ret = requestinterface.findMineByUsername(username);
            R_req_mine_issue r_req_mine_issue = new R_req_mine_issue();
            List<R_req_mine_issue> r_req_mine_issues = My_transfer.castEntity(ret, R_req_mine_issue.class, r_req_mine_issue);
            for(int i = 0; i < r_req_mine_issues.size(); i++)r_req_mine.issues.add(r_req_mine_issues.get(i));
            r_req_mine.status.msg = "查询成功";
            r_req_mine.status.code = 200;
            return r_req_mine;
        }catch (Exception e){
            System.out.println("查找失败");
            r_req_mine.status.msg = "数据库查询失败";
            return r_req_mine;
        }
    }

    @PostMapping("/add")
    public R_req_add add(@RequestHeader("Authorization") String token, @RequestBody P_req_add input){
        String username = TokenUtil.decodeToken(token);
        R_req_add r_req_add = new R_req_add();
        if(input.check() == 0){
            r_req_add.status.code = 400;
            r_req_add.status.msg = "参数错误";
            System.out.println(input.headcount);
            System.out.println(input.description);
            System.out.println(input.commissionFee);
            System.out.println(input.title);
            System.out.println(input.type);
            System.out.println(input.createTime);
            System.out.println(input.endTime);
            return r_req_add;
        }
        try {
            User user = userinterface.findByUsername(username);
            try{
                String time1 = input.createTime.toString();
                System.out.println(time1 + "---->");
                time1 = time1.substring(0, time1.length()-2);
                System.out.println("请求信息创建时间：" + time1);
                String time2 = input.endTime.toString();
                time2 = time2.substring(0, time2.length()-2);
                requestinterface
                        .Add(user.id, username,
                                user.name, input.type, input.title,
                                input.description, input.headcount, time1,
                                time1, time2,
                                //input.image,
                                0, input.commissionFee);
                r_req_add.status.code = 200;
                r_req_add.status.msg = "成功";
                System.out.println("根据创建时间在数据库中查找委托请求的id");
                r_req_add.id = requestinterface.findIdByCreateTime(Timestamp.valueOf(time1));

            }catch (Exception e2){
                System.out.println("写入委托信息失败");
                r_req_add.status.code = 400;
                r_req_add.status.msg = "新建用户委托失败";
            }
        }catch (Exception e){
            r_req_add.status.code = 400;
            r_req_add.status.msg = "新建委托失败,用户数据库中没有该用户的信息";
            System.out.println("error：没有在数据库中找到该用户的id信息");
        }
        return r_req_add;
    }

    @PostMapping("/update")
    public R_req_update update(@RequestBody P_req_update input){
        R_req_update r_req_update = new R_req_update();
        try {
            System.out.println("尝试修改委托");
            long currentTime = System.currentTimeMillis();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
            Date date = new Date(currentTime);
            String nowTime = simpleDateFormat.format(date);
            System.out.println("--当前时间：" + nowTime);
            requestinterface
                    .modify(input.id, input.type, input.title,
                            input.description, input.headcount, My_transfer.cutTimeStamp(input.endTime.toString()),
                            input.commissionFee, nowTime);
            r_req_update.status.code = 200;
            r_req_update.status.msg = "委托修改成功";
        }catch (Exception e){
            System.out.println("修改委托失败");
            r_req_update.status.code = 400;
            r_req_update.status.msg = "委托修改失败";
        }
        return r_req_update;
    }

    @PostMapping("/delete")
    public R_req_delete delete(@RequestHeader("Authorization") String token, @RequestBody P_req_delete input){
        R_req_delete r_req_delete = new R_req_delete();
        r_req_delete.status.code = 400;
        String username = TokenUtil.decodeToken(token);
        try {
            System.out.println("根据id查找该委托的真正发起人，与删除请求者对比");
            String real_username = requestinterface.findUsernameById(input.id);
            if(real_username.equals(username)){
                try {
                    System.out.println("匹配成功，开始修改状态");
                    requestinterface.modifyStateById(input.id, 1);
                    r_req_delete.status.code = 200;
                    r_req_delete.status.msg = "成功";
                }catch (Exception e2){
                    System.out.println("状态修改失败");
                }
            }else {
                r_req_delete.status.msg = "删除失败，用户没有操作这个委托的权限";
            }
        }catch (Exception e){
            System.out.println("根据id的查找失败");

            r_req_delete.status.msg = "委托id找不到";
        }
        return r_req_delete;
    }

    @PostMapping("/reply")
    public R_reply reply(@RequestBody P_reply input){
        R_reply r_reply = new R_reply();
        r_reply.status.code = 400;
        r_reply.status.msg = "";
        try {
            System.out.println("获取请求、响应的状态");
            Integer request_state = requestinterface.findStateById(input.requestId);
            Integer response_state = answerinterface.findStateById(input.responseId);
            if(request_state.intValue() != 0){
                r_reply.status.msg += "该请求不在待响应状态,";
                return r_reply;
            }
            if(response_state.intValue() != 0){
                r_reply.status.msg += "该响应不在待接受状态";
                return r_reply;
            }
            try {
                if(input.operate.equals(0)) {
                    requestinterface.modifyStateById(input.requestId, 2);

                    List<Integer>  ret = answerinterface.findIdByRequestId(input.requestId);

                    for(Integer i : ret){                         //拒绝掉所有的res
                        response_state = answerinterface.findStateById(i);
                        if(response_state == 0)
                            answerinterface.modifyStateById(i,2);
                    }
                }
                answerinterface.modifyStateById(input.responseId, input.operate+1);
                r_reply.status.code = 200;
                r_reply.status.msg = "成功";
            }catch (Exception e){
                System.out.println("修改请求、响应状态失败");
                r_reply.status.msg = "修改请求、响应状态失败";
            }

        }catch (Exception e){
            System.out.println("在数据库中查找失败");
            r_reply.status.msg = "请求id、响应id错误，数据库中没有数据";
        }
        return r_reply;
    }

    @PostMapping("/confirm")
    public R_confirm confirm(@RequestBody P_confirm input){
        R_confirm r_confirm = new R_confirm();
        r_confirm.status.code = 400;
        if(input.check() == 0){
            r_confirm.status.msg = "参数异常";
            return r_confirm;
        }
        try {
            System.out.println("在数据库中查找即将被确认的委托");
            Integer state = requestinterface.findStateById(input.requestId);
            if(state.equals(2)){
                try {
                    System.out.println("在数据库中将委托状态修改为完成");
                    requestinterface.modifyStateById(input.requestId, 3);
                    r_confirm.status.code = 200;
                    r_confirm.status.msg = "成功";
                }catch (Exception e){
                    System.out.println("委托状态修改失败");
                    r_confirm.status.msg = "委托状态修改失败";
                }
            }else {
                r_confirm.status.msg = "委托不处于被响应未完成状态：" + state.toString();
            }
        }catch (Exception e){
            System.out.println("数据库中没有该委托");
            r_confirm.status.msg = "委托id错误，数据库中没有";
        }
        return r_confirm;
    }
}

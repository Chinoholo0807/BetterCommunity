//package com.example.demo.controllor;
//
//import com.example.demo.entity.runuse.My_transfer;
//import com.example.demo.struct.response.response.R_query;
//import com.example.demo.struct.response.response.R_query_issue;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//
//@RestController
//@RequestMapping("/census")
//public class Summaryhandler {
//    @GetMapping("/query")
//    public R_query query(@RequestParam(value = "id", required = false) Integer id,
//                         @RequestParam(value = "requestId", required = false) Integer requestId) {
//        R_query r_query = new R_query();
//        r_query.status.code = 400;
//        if (id != null) {
//            System.out.println("通过响应id查找");
//            try {
//                List<Object[]> ret = answerinterface.findQueryById(id);
//                R_query_issue r_query_issue = new R_query_issue();
//                List<R_query_issue> r_query_issueList = My_transfer.castEntity(ret, R_query_issue.class, r_query_issue);
//                for (int i = 0; i < r_query_issueList.size(); i++)
//                    r_query.resps.add(r_query_issueList.get(i));
//                r_query.status.code = 200;
//                r_query.status.msg = "成功";
//            } catch (Exception e) {
//                System.out.println("查询错误");
//                r_query.status.msg = "通过响应id查找失败";
//            }
//        }
//    }
//}

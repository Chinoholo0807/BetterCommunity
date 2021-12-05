package com.example.demo.controllor;

import com.example.demo.entity.User;
import com.example.demo.entity.runuse.Login_user;
import com.example.demo.entity.runuse.TokenUtil;
import com.example.demo.society.method.Userinterface;
import com.example.demo.society.method.runuse.Login_user_interface;
import com.example.demo.struct.Response;
import com.example.demo.struct.posttype.user.P_login;
import com.example.demo.struct.posttype.user.P_register;
import com.example.demo.struct.posttype.user.P_update;
import com.example.demo.struct.response.user.R_info;
import com.example.demo.struct.response.user.R_login;
import com.example.demo.struct.response.user.R_register;
import com.example.demo.struct.response.user.R_update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class Userhandler {
    @Autowired
    public Userinterface userinterface;
    @Autowired
    public Login_user_interface login_user_interface;

    @PostMapping("/test")//测试
    public R_login mytest(@RequestBody P_register input ){
        try{
            login_user_interface.Add(input.username, input.certificateType);
            R_login r_login = new R_login();
            r_login.status.code = 200;
            r_login.status.msg = "不知道";
            r_login.token = TokenUtil.createToken("feng");
            return r_login;
        }catch (Exception e){
            System.out.println("注册失败");
            R_login r_login = new R_login();
            r_login.status.code = 400;
            r_login.status.msg = "注册失败";
            r_login.token = TokenUtil.createToken("feng");
            return r_login;
        }
    }

    @GetMapping("/noToken")
    @PostMapping("/noToken")
    public Response noToken(){
        Response response = new Response();
        response.status.code = 400;
        response.status.msg = "token异常";
        return response;
    }

    @PostMapping("/login")//登录用户
    public R_login UserLogin(@RequestBody P_login input){
        R_login r_login = new R_login();
        if(input.check() == 0){
            r_login.status.code = 400;
            r_login.status.msg = "参数异常";
            return r_login;
        }
        try{
            System.out.println("login：尝试从 用户 数据库中查找用户信息");
            User get_user = userinterface.findByUsername(input.username);
            if(get_user.password.equals(input.password)) {
                try{
                    System.out.println("login：尝试从 登录状态 数据库中查找用户信息");
                    Login_user login_user = login_user_interface.findByUsername(input.username);
                    if(login_user.state == -99) {//用户已经在其他地方登录
                        r_login.status.code = 600;
                        r_login.status.msg = "用户已在其他地方登录";
                    }else{                      //登录成功，将token写入数据库
                        System.out.println("login：登录成功！  准备向数据库中写入token");
                        String create_token = TokenUtil.createToken(input.username);
                        r_login.token = create_token;
                        try{
                            System.out.println("login：向数据库中写入token");
                            login_user_interface.modifyToken(input.username, create_token);
                            login_user_interface.modifyState(input.username, 1);
                            r_login.status.code = 200;
                            r_login.status.msg = "登录成功";

                        }catch (Exception e3){  //token写入数据库失败
                            System.out.println("写入token失败");
                            r_login.status.code = 400;
                            r_login.status.msg = "写入用户登录状态的token失败";
                        }
                    }
                }catch (Exception e){           //当前登录用户数据库中没有信息
                    System.out.println("login：登录状态数据库中没有信息，决定新建");
                    try {
                        String create_token = TokenUtil.createToken(input.username);
                        r_login.token = create_token;
                        System.out.println("login：向数据库中写入token");
                        login_user_interface.Add(input.username, 1, create_token);
                        r_login.status.code = 200;
                        r_login.status.msg = "登录成功";
                    }catch (Exception e2){
                        System.out.println("写入token失败");
                        r_login.status.code = 400;
                        r_login.status.msg = "修改用户登录状态失败";
                    }
                }
            }else{
                System.out.println("密码错误");
                r_login.status.msg = "密码错误";
                r_login.status.code = 500;
            }
        }catch (Exception e){
            System.out.println("login：数据库中没有该用户数据:" + e.getMessage());
            r_login.status.code = 500;
            r_login.status.msg = "用户名不存在";
        }
        return r_login;
    }

    @PostMapping("/register")
    public R_register UserRegister(@RequestBody P_register input){
        System.out.println("开始尝试注册");
        System.out.println("--时间参数：" + input.regTime);
        R_register r_register = new R_register();
        if(input.check() == 0){
            r_register.status.code = 400;
            r_register.status.msg = "参数错误";
            return r_register;
        }
        try{
            String reg_time = input.regTime.toString();
            reg_time = reg_time.substring(0, reg_time.length()-2);
            System.out.println("--reg_time --->> " + reg_time);
            String up_time = input.updateTime.toString();
            up_time = up_time.substring(0, up_time.length()-2);
            userinterface.Add(input.username, input.password, input.name,
                                input.certificateType, input.certificateNum, input.phoneNum,
                                input.introduction, input.regCity, input.regCommunity,
                                reg_time, up_time);
            r_register.status.code = 200;
            r_register.status.msg = "注册成功";
        }catch (Exception e){
            System.out.println("注册失败");
            r_register.status.code = 400;
            r_register.status.msg = "用户名重复";
            System.out.println("————————————————————注册时加入数据库失败————————————————————————\n \n \n");
        }
        return r_register;
    }

    @GetMapping("/info")
    public R_info UserInfo(@RequestHeader("Authorization") String token, @RequestParam(value = "username", required = false) String input_username){
        String username;
        R_info r_info = new R_info();
        if(input_username == null || input_username.isEmpty()) {
            username = TokenUtil.decodeToken(token);
            System.out.println("从token获取用户名:" + username + "(" + username.length() + ")");

        }
        else {
            username = input_username;
            System.out.println("从body获取用户名:" + username + "(" + username.length() + ")");
        }
        try{
//            User user_info = userinterface.findByUsername(username);
            List<User> users = userinterface.findsByUsername(username);
            System.out.println("获取到用户信息数量：" + users.size());
            User user_info = users.get(0);
            r_info.username = user_info.username;
            r_info.type = user_info.type;
            r_info.name = user_info.name;
            r_info.certificateType = user_info.certificate_type;
            r_info.certificateNum = user_info.certificate_num;
            r_info.phoneNum = user_info.phone_num;
            r_info.userLevel = user_info.user_level;
            r_info.introduction = user_info.introduction;
            r_info.regCity = user_info.reg_city;
            r_info.regCommunity = user_info.reg_community;
            r_info.regTime = user_info.reg_time;
            r_info.updateTime = user_info.update_time;
            r_info.status.code = 200;
            r_info.status.msg = "成功";
            return r_info;

        }catch (Exception e){
            System.out.println("用户信息查询请求错误，没有找到对应用户名的信息:" + e.getMessage());
            r_info.username = "error!!";
            r_info.status.code = 400;
            r_info.status.msg = "在数据库中查找失败";
            return r_info;
        }
    }

    @PostMapping("/update")
    public R_update update(@RequestBody P_update input, @RequestHeader("Authorization") String token){
        R_update r_update = new R_update();
        if(input.check() == 0){
            r_update.status.code = 400;
            r_update.status.msg = "参数错误";
            return r_update;
        }
        try {
            String username = TokenUtil.decodeToken(token);
            System.out.println("尝试修改"+username+"用户个人信息");

            long currentTime = System.currentTimeMillis();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
            Date date = new Date(currentTime);
            String nowTime = simpleDateFormat.format(date);

            userinterface.modify(username, input.phoneNum, input.introduction, input.password,nowTime);
            r_update.status.code = 200;
            r_update.status.msg = "用户信息修改成功";
        }catch (Exception e){
            r_update.status.code = 400;
            r_update.status.msg = "用户信息修改失败";
        }
        return r_update;
    }
}

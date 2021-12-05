package com.example.demo.controllor;

import com.example.demo.entity.runuse.Login_user;
import com.example.demo.entity.runuse.TokenUtil;
import com.example.demo.entity.runuse.XjxccException;
import com.example.demo.society.method.runuse.Login_user_interface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

public class LogCostInterceptor implements HandlerInterceptor {

    @Autowired
    public Login_user_interface login_user_interface;

    long start = System.currentTimeMillis();

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if(httpServletRequest.getRequestURI().equals("/user/noToken")){
            System.out.println("--------------------------------------------------开始处理noToken异常");
            return true;
        }
        start = System.currentTimeMillis();
        System.out.println("请求的url为：" + httpServletRequest.getRequestURI());

        //打印请求头
//        Enumeration<String> enums = httpServletRequest.getHeaderNames();
//        while (enums.hasMoreElements()) {
//            String name = enums.nextElement();
//            System.out.println(name + ": {}" + httpServletRequest.getHeader(name));
//        }
//
//        System.out.println(httpServletRequest.getHeader("access-control-request-headers"));

//       return true;

        try{
            String token = httpServletRequest.getHeader("Authorization");
            System.out.println("获得token并开始解码");
            String username = TokenUtil.decodeToken(token);
            System.out.println("解码成功得到token没有错误： " + token + "\n--decode username: " + username);
            return true;
        }catch (Exception e){
            System.out.println("error1:header部分不包含token字段、或者token字段过期");
            try{
                String url = httpServletRequest.getRequestURI();
                if(url.equals("/user/login")
                        || url.equals("/user/register")
                        || url.equals("/login")
                        || url.equals("/register")
                        || url.equals("/user/test")
                        || url.equals("/user/noToken")){
                    return true;
                }else {
                    System.out.println("error2:只有登录、注册请求才能不包含token");

                    httpServletRequest.getRequestDispatcher("/user/noToken").forward(httpServletRequest, httpServletResponse);
                    return  false;
//                    handleFalseResponse(httpServletResponse);
//                    return false;
                }
            }catch (Exception e2){
                System.out.println("error3:请求头中不包含url部分");
                handleFalseResponse(httpServletResponse);
                return false;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("Interceptor cost="+(System.currentTimeMillis()-start));
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

    private void handleFalseResponse(HttpServletResponse response)
            throws Exception {
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write("{\"status\":{\"code\":400,\"msg\":\"token验证失败\"}}");
        response.getWriter().flush();
    }
}
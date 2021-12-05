package com.example.demo.entity.runuse;

import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.Signature;
import java.util.Date;


public class TokenUtil {
    public static long living_time = 1000*60*60*20;//有效时间，（天）
    public static String signature = "admin";//签名信息，用于解密

    public static String createToken(String username){
        JwtBuilder jwtBuilder = Jwts.builder();
        String token = jwtBuilder
                //Header部分
                .setHeaderParam("type", "JWT")
                .setHeaderParam("algorithm", "HS256")
                //payload部分
                .claim("username", username)
                .claim("role", "admin")
                .setSubject("admin")//主题
                .setExpiration(new Date(System.currentTimeMillis()+living_time))//有效时间
                .setId(username)//jwt的id
                //signature部分
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();//拼接3部分

        return token;
    }

    public static String decodeToken(String token){
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
//        body.get("username");
//        body.getId();
//        body.getSubject();
        String username = body.get("username", String.class);
        return username;
    }
}

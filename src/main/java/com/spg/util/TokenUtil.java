package com.spg.util;


import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.HashMap;
import java.util.Map;

/**
 *  Token Utils
 * Created by Administrator on  2018/9/25
 */
public class TokenUtil {

    /**
     * 私钥
     */
    private static final String SECRET = "liubing&xufei";

    /**
     * 生成Token
     * @param claims
     * @return
     */
    public static String generateToken(Map<String,Object> claims){
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setClaims(claims);
        //参数：签名算法，私钥
        jwtBuilder.signWith(SignatureAlgorithm.HS512,SECRET);
        return jwtBuilder.compact();
    }

    /**
     * 解析Token
     * @param token
     * @return
     */
    public static Map<String,Object> getClaimsFromToken(String token){
        Map<String,Object> claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        return claims;
    }

    public static Map<String ,Object> getMap(String hash ,String openid){
        Map<String, Object> claims = new HashMap<>(2<<4);
        claims.put("hash", hash);
        claims.put("openid", openid);
        claims.put("timestamp" ,System.currentTimeMillis());
        return claims;
    }

    /**
     *
     * @param time
     * @return
     */
    public static boolean checkTimeStamp(Long time) {
        // 有效期: 1个小时,单位: ms
        long expires = 60 * 1000 * 60;
        //两者相差的时间,单位(ms)
        long timestamp = System.currentTimeMillis() - time;
        if(timestamp > expires){
            //过期
            return false;
        }else {
            return true;
        }
    }
}

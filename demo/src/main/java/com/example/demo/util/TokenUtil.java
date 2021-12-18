package com.example.demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {

    /**
     * 过期时间60分钟
     */
    private static final long EXPIRE_TIME=60 * 60 *1000;

    private static final String TOKEN_SECRET="Token";


    /**
     * 产生token
     * @param userName
     * @param userId
     * @return
     */
    public static String sign(String userName,String userId){

        try{

            //设置15分钟失效
            Date date=new Date(System.currentTimeMillis()+EXPIRE_TIME);
            //私钥及加密算法
            Algorithm algorithm=Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String,Object> header=new HashMap<>();
            header.put("typ","JWT");
            header.put("alg","HS256");
            //附带username和userid信息,存储到token中，生成签名
            return JWT.create()
                    .withHeader(header)
                    //存储自己想要留存给客户端浏览器的内容
                    .withClaim("userName",userName)
                    .withClaim("userId",userId)
                    .withExpiresAt(date)
                    .sign(algorithm);



        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }


    /**
     * token校验是否正确
     * @param token
     * @return
     */

    public static boolean verify(String token){

        try {
            Algorithm algorithm=Algorithm.HMAC256(TOKEN_SECRET);

            JWTVerifier verifier =JWT.require(algorithm).build();
            DecodedJWT decodedJWT =verifier.verify(token);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }


    /**
     * 获取token中信息 userName
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userName").asString();
        } catch (JWTDecodeException e) {
            e.getStackTrace();
        }
        return null;
    }


    /**
     * 获取token中信息 userId
     * @param token
     * @return
     */
    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asString();
        } catch (JWTDecodeException e) {
            e.getStackTrace();

        }
        return null;
    }


}


//import com.auth0.jwt.JWT;
//
//import com.auth0.jwt.algorithms.Algorithm;
//import com.example.demo.entity.User;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import java.util.Date;
//import java.util.List;
//
//@Component
//public class TokenUtil {
//    /*
//     * 生成token
//     * */
//    public String generateToken(User user){
//        Date start  = new Date() ;
//        long currentTime = System.currentTimeMillis() + 60*60*1000 ; //一小时的有效时间
//        Date end = new Date(currentTime) ;
//        String token = "" ;
//        token = JWT.create()
//                .withAudience(Integer.toString(user.getId()))
//                .withAudience(user.getUsername())
//                .withIssuedAt(start)
//                .withExpiresAt(end)
//                .sign(Algorithm.HMAC256(user.getPassword()));
//        return token ;
//    }
//
//    /*
//     * 获取指定token中某个属性值
//     * */
//    public static String get(String token , String key){
//        List<String> list = JWT.decode(token).getAudience() ;
//        String userId = list.get(0) ;
//        return userId ;
//    }
//    /*
//     * 获取request
//     * */
//    public static HttpServletRequest getRequest(){
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        return requestAttributes == null ? null : requestAttributes.getRequest() ;
//    }
//
//    /*
//     * 获取token
//     * */
//    public String getToken(HttpServletRequest request)  {
//        Cookie[] cookies = request.getCookies() ;
//        for(Cookie c : cookies){
//            if(c.getName() == "token"){
//                return c.getValue() ;
//            }
//        }
//        return null ;
//    }
//
//}

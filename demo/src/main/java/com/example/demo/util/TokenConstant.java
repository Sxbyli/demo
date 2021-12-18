package com.example.demo.util;


import java.util.HashMap;
import java.util.Map;


public class TokenConstant {

    private static Map<String,String> map=new HashMap();

    public static String getTokenString(){
        return map.get("accessToken");
    }

    public static void updateTokenMap(String token){
        map.put("accessToken",token);
    }

}



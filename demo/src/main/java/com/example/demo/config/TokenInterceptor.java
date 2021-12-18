package com.example.demo.config;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.util.TokenConstant;
import com.example.demo.util.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        httpServletResponse.setCharacterEncoding("UTF-8");
        String token=httpServletRequest.getHeader("accessToken");
        if (null==token){
            Map<String,Object> map=new HashMap<>();
            map.put("data","Token is null!");
            map.put("code","401");
            httpServletResponse.getWriter().write(JSONObject.toJSONString(map));
            return false;
        }else {
            boolean result= TokenUtil.verify(token);

            if (result){
                //更新存储的token信息
                TokenConstant.updateTokenMap(token);
                return true;
            }

            Map<String,Object> map=new HashMap<>();
            map.put("data","Token is null!");
            map.put("code","401");
            httpServletResponse.getWriter().write(JSONObject.toJSONString(map));
            return false;

        }


    }

    //    试图渲染之后执行
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    //    在请求处理之后,视图渲染之前执行
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }


}

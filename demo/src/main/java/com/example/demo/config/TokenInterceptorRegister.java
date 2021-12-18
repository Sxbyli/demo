package com.example.demo.config;


import com.example.demo.config.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class TokenInterceptorRegister implements WebMvcConfigurer {


//    //解决跨域问题
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedHeaders("Content-Type","X-Requested-With","accept,Origin","Access-Control-Request-Method","Access-Control-Request-Headers","token")
//                .allowedMethods("*")
//                .allowedOrigins("*")
////是否允许使用cookie
//                .allowCredentials(true);
//    }


    @Autowired
    private TokenInterceptor tokenInterceptor;

//    // 这个方法是用来配置静态资源的，比如html，js，css，等等
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("进入token拦截器");
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/islogin")
                .excludePathPatterns("/register");
    }

}

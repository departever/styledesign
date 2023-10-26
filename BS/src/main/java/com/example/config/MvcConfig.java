package com.example.config;

import com.example.listener.HttpSessionListener;
import com.example.util.LoginHandlerInterceptor;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // 添加视图控制
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:login");
        registry.addViewController("login").setViewName("/login");
        registry.addViewController("index").setViewName("/index");
        registry.addViewController("view/add").setViewName("/view/add");
    }

    //    实现方法添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * addPathPatterns():为拦截器应包含在其中的 URL 添加模式
         * excludePathPatterns():为排除的URL添加模式
         */
        String[] excludes = new String[]{"/", "/login", "/toLogin", "/handleLogin", "/online", "/**/*.html", "/**/*.js", "/**/*.css", "/**/*.png", "/**/*.jpg"};
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludes);
    }

    @Bean
    public ServletListenerRegistrationBean getListener() {
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new HttpSessionListener());
        return servletListenerRegistrationBean;
    }
}
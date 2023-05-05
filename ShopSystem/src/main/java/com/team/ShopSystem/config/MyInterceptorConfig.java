package com.team.ShopSystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
//public class MyInterceptorConfig implements WebMvcConfigurer {
//
//    @Autowired
//    private JwtValidateInterceptor jwtValidateInterceptor;

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        InterceptorRegistration registration = registry.addInterceptor(jwtValidateInterceptor);
//        registration.addPathPatterns("/**")
//                .excludePathPatterns(
//                        "/user",
//                        "/user/login",
//                        "/user/info",
//                        "/user/logout",
//                        "/error",
//                        "/swagger-ui/**",
//                        "/swagger-resources/**",
//                        "/v3/**"
//                );//拦截除了字符串数组外的访问,后四个是针对swagger避免拦截的设置
//    }
//}
@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer {

    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("file:" + UPLOAD_FOLDER);
    }
}

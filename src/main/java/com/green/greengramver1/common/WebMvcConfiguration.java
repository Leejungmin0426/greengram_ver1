package com.green.greengramver1.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.beans.BeanProperty;

@Configuration // 빈등록이 됨. WebMvcConfiguration이 객체화가 실행. 애노테이션 @Bean은 리턴타입. void는 리턴타입을 적을 순 없다. 빈등록은 주소값 받기 위해서.
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer){
        // RestController의 모든 URL에 "/api" prefix를 설정
        configurer.addPathPrefix("api", HandlerTypePredicate.forAnnotation(RestController.class));
    }
}

package top.newpointer.farm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.newpointer.farm.interceptor.TokenInterceptor;

@Configuration
public class AuthConfig implements WebMvcConfigurer {

    @Bean
    public TokenInterceptor initAuthInterceptor(){
        return new TokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(initAuthInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/identity/**");
    }
}
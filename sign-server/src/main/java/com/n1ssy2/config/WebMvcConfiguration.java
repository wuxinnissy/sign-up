package com.n1ssy2.config;

/**
 * ClassName: WebMvcConfiguration
 * Package: com.n1ssy2.config
 * Description:
 *
 * @Auther: N1ssy2
 * @Create: 2023/12/20 23:31
 * @Version: 1.0
 */

import com.n1ssy2.JacksonObjectMapper;
import com.n1ssy2.interceptor.JwtTokenStudentInterceptor;
import com.n1ssy2.interceptor.JwtTokenTeacherInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private JwtTokenTeacherInterceptor jwtTokenTeacherInterceptor;
    @Autowired
    private JwtTokenStudentInterceptor jwtTokenStudentInterceptor;

    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenTeacherInterceptor)
                .addPathPatterns("/teacher/**")
                .excludePathPatterns("/teacher/login");
        registry.addInterceptor(jwtTokenStudentInterceptor)
                .addPathPatterns("/student/**")
                .excludePathPatterns("/student/login");
    }

    /**
     * 通过knife4j生成接口文档
     *
     * @return
     */
    @Bean
    public Docket docket() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("签到项目接口文档")
                .version("1.0")
                .description("签到项目接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.n1ssy2.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    /**
     * 设置静态资源映射
     *
     * @param registry
     */
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}

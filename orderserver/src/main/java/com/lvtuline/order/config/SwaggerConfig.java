package com.lvtuline.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //  加了api注解Controller
                .apis(RequestHandlerSelectors.basePackage("com.lvtuline.order.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 接口文档头
                .title("lvtuline接口文档")
                // 接口文档描述
                .description("lvtuline接口文档")
                // 服务器地址
                .termsOfServiceUrl("https://www.lvtuline.com")
                // 项目版本号
                .version("1.0")
                .build();
    }

}

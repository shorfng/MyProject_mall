package com.loto.mall.service.goods.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Author：蓝田_Loto
 * <p>Date：2022-04-15 16:39</p>
 * <p>PageName：SwaggerConfig.java</p>
 * <p>Function：</p>
 */

@Configuration
@EnableSwagger2 //开启Swagger
@EnableSwaggerBootstrapUI
public class SwaggerConfig {
    // http://localhost:8081/doc.html
    // 配置swagger2核心配置
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2) // 指定api类型位swagger2
                .apiInfo(apiInfo())                    // 用于定义api文档汇总信息
                .select().apis(RequestHandlerSelectors.basePackage("com.loto.mall")) // 指定生成文档的 controller
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("mall项目 - 接口api") // 文档标题
                .contact(new Contact("蓝田_Loto", // 作者
                        "",  // URL
                        "shorfng@126.com")) // email
                .description("mall项目 - 接口api")     // 详细信息
                .version("1.0.0")                    // 文档版本号
                .termsOfServiceUrl("")               // 网站地址
                .build();
    }
}

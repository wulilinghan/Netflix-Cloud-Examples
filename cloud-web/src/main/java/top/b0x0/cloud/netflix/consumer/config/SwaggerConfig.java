package top.b0x0.cloud.netflix.consumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * 只在dev和test,native,local环境下开启
 *
 * @author ManJiis
 * @since 2021-07-23
 */
@Configuration
//@EnableSwagger2
//@EnableSwaggerBootstrapUI
//@EnableKnife4j
@EnableSwagger2WebMvc
//@Import(BeanValidatorPluginsConfiguration.class)
//@Profile({"dev", "test", "native", "local"})
public class SwaggerConfig {

    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 扫描controller路径
                .apis(RequestHandlerSelectors.basePackage("top.b0x0.cloud.netflix.consumer.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * name:开发者姓名
     * url:开发者网址
     * email:开发者邮箱
     *
     * @return /
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 标题
                .title("Netflix Cloud Examples接口文档")
                .build();
    }

}
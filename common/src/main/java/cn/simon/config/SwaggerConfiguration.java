package cn.simon.config;


import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.*;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon
 */
@Component
@Data
@EnableOpenApi
public class SwaggerConfiguration {


    /**
     * 2admin
     */
    @Bean
    public Docket adminApiDoc(){
        return new Docket(DocumentationType.OAS_30)
                .groupName("管理端接口文档")
                .pathMapping("/")
                //是否开启Swagger,可以通过变量去控制器,线上关闭
                .enable(true)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.simon"))
                .paths(PathSelectors.ant("/admin/**"))
                .build();
    }


    /**
     * 2c
     */
    @Bean
    public Docket webApiDoc(){
        return new Docket(DocumentationType.OAS_30)
                .groupName("c端接口文档")
                .pathMapping("/")
                //是否开启Swagger,可以通过变量去控制器,线上关闭
                .enable(true)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.simon"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .globalRequestParameters(globalRequestParameters())
                .globalResponses(HttpMethod.GET,globalResponseMessage())
                .globalResponses(HttpMethod.POST,globalResponseMessage());
    }


    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("基础营销架构")
                .description("微服务接口文档")
                .contact(new Contact("simon","","simon_0414@163.com"))
                .version("v1.0")
                .build();
    }

    /**
     * 配置全局通用参数
     */
    private List<RequestParameter> globalRequestParameters(){
        List<RequestParameter> parameters = new ArrayList<>();
        parameters.add(new RequestParameterBuilder()
                .name("token")
                .description("登录令牌")
                .in(ParameterType.HEADER)
                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                .required(false)
                .build());

        return parameters;
    }

    /**
     * 生成通用的响应信息
     */

    private List<Response> globalResponseMessage(){
        List<Response> responses = new ArrayList<>();
        responses.add(new ResponseBuilder().code("4xx").description("请求错误,根据code和msg检查").build());
        return responses;
    }
}

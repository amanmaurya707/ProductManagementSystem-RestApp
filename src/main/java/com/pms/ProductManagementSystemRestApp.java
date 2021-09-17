package com.pms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sun.org.apache.bcel.internal.generic.Select;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableCaching
//@CrosssOrigin("*")//globally available for entire application any domain/origin is allowed. 
public class ProductManagementSystemRestApp implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(ProductManagementSystemRestApp.class, args);
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
	registry.addMapping("/*").allowedOrigins("*");
	}
	
	@Bean//for reg doc type in ApplicationContext for creating spring doc
	public Docket docket()
	{
		//customizing api doc.
		ApiInfoBuilder builder=new ApiInfoBuilder();
		builder.title("Product Management Rest Doc.");
		builder.version("2.0");
		builder.description("this rest api is use for crud operation on products");
		builder.license("amanmaurya.org");
		builder.licenseUrl("http://amanmaurya.org/");
		ApiInfo apiInfo=builder.build();
		
		return new Docket(DocumentationType.SWAGGER_2)
		.select()
		.apis(RequestHandlerSelectors.basePackage("com.pms"))//where to search rest controller
		.paths(PathSelectors.any())//which path/endpoints is included for creating doc
		.build()//create/build doc
		.apiInfo(apiInfo);
	}

}

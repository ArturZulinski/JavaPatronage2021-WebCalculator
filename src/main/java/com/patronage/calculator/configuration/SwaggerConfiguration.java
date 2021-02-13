package com.patronage.calculator.configuration;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,
                        newArrayList(
                                new ResponseMessageBuilder()
                                        .code(200).message("Request executed correctly").build(),
                                new ResponseMessageBuilder()
                                        .code(404).message("Requested resource not found").build(),
                                new ResponseMessageBuilder()
                                        .code(500).message("Internal server error").build()))
                .globalResponseMessage(RequestMethod.POST,
                        newArrayList(
                                new ResponseMessageBuilder()
                                        .code(201).message("Resource created successfully").build(),
                                new ResponseMessageBuilder()
                                        .code(400).message("Inputted data validation error").build(),
                                new ResponseMessageBuilder()
                                        .code(404).message("Requested resource not found").build(),
                                new ResponseMessageBuilder()
                                        .code(500).message("Internal server error").build()))
                .globalResponseMessage(RequestMethod.DELETE,
                        newArrayList(
                                new ResponseMessageBuilder()
                                        .code(200).message("Resource deleted successfully").build(),
                                new ResponseMessageBuilder()
                                        .code(404).message("Requested resource not found").build(),
                                new ResponseMessageBuilder()
                                        .code(500).message("Internal server error").build()))
                .globalResponseMessage(RequestMethod.PUT,
                        newArrayList(
                                new ResponseMessageBuilder()
                                        .code(200).message("Resource updated successfully").build(),
                                new ResponseMessageBuilder()
                                        .code(400).message("Inputted data validation error").build(),
                                new ResponseMessageBuilder()
                                        .code(404).message("Requested resource not found").build(),
                                new ResponseMessageBuilder()
                                        .code(500).message("Internal server error").build()));
    }


    private ApiInfo apiInfo() {
        Contact contact = new Contact("Artur Żuliński",
                "https://github.com/ArturZulinski/JavaPatronage2021-WebCalculator",
                "artur.zulinski@gmail.com");
        return new ApiInfoBuilder()
                .title("WebCalculator by Artur Żuliński")
                .description("Web application for second task in Java Patronage 2021")
                .contact(contact)
                .build();
    }
}
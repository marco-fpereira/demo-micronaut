package com.demo.controller;

import com.demo.service.HelloWorldService;
import io.micronaut.context.annotation.Property;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/hello")
public class HelloWorldController {

    private final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @Property(name = "hello.world.message")
    private String helloFromConfig;

    @Inject
    private HelloWorldService service;

    @Get(produces = MediaType.TEXT_PLAIN)
    public String hello(){
        logger.info("Called the hello world API");
        return service.helloFromService();
    }

    @Get(uri = "/config", produces = MediaType.TEXT_PLAIN)
    public String helloFromConfig(){
        logger.info("Called the hello world from config: " + helloFromConfig);
        return helloFromConfig;
    }

}

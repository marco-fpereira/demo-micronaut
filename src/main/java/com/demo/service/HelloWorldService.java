package com.demo.service;

import jakarta.inject.Singleton;

@Singleton
public class HelloWorldService {

    public String helloFromService() {
        return "Hello world from service!";
    }
}

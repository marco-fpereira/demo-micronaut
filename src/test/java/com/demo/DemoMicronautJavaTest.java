package com.demo;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class DemoMicronautJavaTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    public void helloWorldEndpointRespondsWithProperContent(){
        var response = client.toBlocking().retrieve("/hello");
        assertEquals("Hello world from service!", response);
    }

    @Test
    public void helloWorldEndpointRespondsWithProperStatusCodeAndContent() {
        var response = client.toBlocking().exchange("/hello", String.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Hello world from service!", response.body());
    }

    @Test
    public void helloWorldFromConfigEndpointRespondsWithProperContent(){
        var response = client.toBlocking().retrieve("/hello/config", String.class);
        assertEquals("hello from config!", response);
    }

    @Test
    public void helloWorldFromConfigEndpointRespondsWithProperStatusCodeAndContent(){
        var response = client.toBlocking().exchange("/hello/config", String.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("hello from config!", response.body());

    }

}

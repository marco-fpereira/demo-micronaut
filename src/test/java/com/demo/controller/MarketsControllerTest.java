package com.demo.controller;

import com.demo.model.Symbol;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class MarketsControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    public void testReturnsListOfMarkets(){
        List result = client.toBlocking().retrieve("/markets", List.class);
        final List<LinkedHashMap<String, String>> markets = result;

        assertEquals(7, result.size());
        assertThat(markets)
                .extracting(entry -> entry.get("value"))
                .containsExactlyInAnyOrder("APPL", "AMZN", "FB", "GOOG", "MSFT", "NFLX", "TSLA");
    }
}
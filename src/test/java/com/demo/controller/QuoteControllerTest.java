package com.demo.controller;

import com.demo.exception.CustomException;
import com.demo.model.Quote;
import com.demo.model.Symbol;
import com.demo.store.InMemoryStore;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static io.micronaut.http.HttpRequest.GET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
public class QuoteControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    InMemoryStore store;

    @Test
    public void returnsQuotePerSymbol(){
        final Quote apple = initRandomQuote("APPL");
        store.updateQuote(apple);

        final Quote appleResult = client.toBlocking().retrieve(GET("/quote/APPL"), Quote.class);
        assertThat(apple.getSymbol().getValue()).isEqualTo(appleResult.getSymbol().getValue());
    }

    @Test
    public void returnsBadRequest(){
        try {
            client.toBlocking().retrieve(
                    GET("quote/NONEXISTENT"),
                    Argument.of(Quote.class),
                    Argument.of(CustomException.class)
            );
        } catch (HttpClientResponseException e ){
            Optional<CustomException> optionalResponse = e.getResponse().getBody(CustomException.class);
            assertTrue(optionalResponse.isPresent());

            CustomException response = optionalResponse.get();
            assertEquals(response.getStatus(), HttpResponse.badRequest().getStatus().getCode());
            assertEquals(response.getError(), HttpStatus.BAD_REQUEST.name());
            assertEquals(response.getErrorMessage(), "Informed quote doesn't exist");
            assertEquals(response.getPath(), "/quote/NONEXISTENT");
        }
    }

    private Quote initRandomQuote(String symbol) {
        return new Quote(
                new Symbol(symbol),
                randomValue(),
                randomValue(),
                randomValue(),
                randomValue()
        );
    }

    private BigDecimal randomValue(){
        return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1,100));
    }
}
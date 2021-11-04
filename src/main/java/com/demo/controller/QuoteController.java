package com.demo.controller;

import com.demo.exception.CustomException;
import com.demo.model.Quote;
import com.demo.store.InMemoryStore;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Controller("/quote")
public class QuoteController {

    private final Logger logger = LoggerFactory.getLogger(QuoteController.class);

    @Inject
    private InMemoryStore store;

    @Get("/{symbol}")
    public HttpResponse<?> getQuote(@PathVariable(value = "symbol") String symbol){
        logger.info("Called the quote API endpoint, that return a quote that contains a specific symbol");
        Optional<Quote> optionalQuote = store.fetchQuote(symbol);
        if(optionalQuote.isPresent()) {
            logger.info("Informed quote exists");
            return HttpResponse.ok(optionalQuote.get());
        }
        logger.info("Informed quote doesn't exist");
        CustomException e = new CustomException(
                HttpStatus.BAD_REQUEST.getCode(),
                HttpStatus.BAD_REQUEST.name(),
                "Informed quote doesn't exist",
                "/quote/" + symbol);
        return HttpResponse.badRequest(e);
    }

}

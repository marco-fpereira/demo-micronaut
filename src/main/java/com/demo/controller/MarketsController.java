package com.demo.controller;

import com.demo.model.Symbol;
import com.demo.store.InMemoryStore;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller("/markets")
public class MarketsController {

    private final Logger logger = LoggerFactory.getLogger(MarketsController.class);

    @Inject
    private InMemoryStore store;

    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<Symbol>> all (){
        logger.info("Called endpoint that returns a list of all existing symbols");
        return HttpResponse.ok(store.getAllSymbols());
    }
}

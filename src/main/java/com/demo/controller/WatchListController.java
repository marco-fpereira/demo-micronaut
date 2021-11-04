package com.demo.controller;

import com.demo.model.WatchList;
import com.demo.store.InMemoryStore;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Controller("watchlist")
public class WatchListController {

    private final Logger logger = LoggerFactory.getLogger(WatchListController.class);

    static final UUID ACCOUNT_ID = UUID.randomUUID();

    private final InMemoryStore store;

    public WatchListController(InMemoryStore store) {
        this.store = store;
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public WatchList get(){
        logger.info("Called endpoint that returns a WatchList according to an account id! ");
        return store.getWatchList(ACCOUNT_ID);
    }

    @Put(
        consumes = MediaType.APPLICATION_JSON,
        produces = MediaType.APPLICATION_JSON
    )
    public WatchList update(@Body WatchList watchList){
        logger.info("Called endpoint that updates a WatchList according to an account id! ");
        return store.updateWatchList(ACCOUNT_ID, watchList);
    }

    @Delete(
        value = "{accountId}",
        consumes = MediaType.APPLICATION_JSON,
        produces = MediaType.APPLICATION_JSON
    )
    public void delete(@PathVariable UUID accountId){
        logger.info("Called endpoint that deletes a WatchList according to an account id! ");
        store.deleteWatchList(accountId);
    }

}

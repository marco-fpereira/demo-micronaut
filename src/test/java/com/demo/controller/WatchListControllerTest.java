package com.demo.controller;


import com.demo.model.Symbol;
import com.demo.model.WatchList;
import com.demo.store.InMemoryStore;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.micronaut.http.HttpRequest.*;
import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class WatchListControllerTest {

    static final UUID TEST_ACCOUNT_ID = WatchListController.ACCOUNT_ID;

    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    InMemoryStore store;

    List<Symbol> symbols = Stream.of("APPL", "AMZN", "FB").map(Symbol::new).collect(Collectors.toList());

    @Test
    public void returnsEmptyWatchList(){
        WatchList watchlist = client.toBlocking().retrieve("/watchlist", WatchList.class);
        assertTrue(watchlist.getSymbols().isEmpty());
        assertTrue(store.getWatchList(TEST_ACCOUNT_ID).getSymbols().isEmpty());
    }

    @Test
    public void returnsWatchListForAccount(){

        WatchList watchList = new WatchList(symbols);
        store.updateWatchList(TEST_ACCOUNT_ID, watchList);

        WatchList result = client.toBlocking().retrieve("/watchlist", WatchList.class);
        assertEquals(3, result.getSymbols().size());
        assertEquals(3, store.getWatchList(TEST_ACCOUNT_ID).getSymbols().size());
    }

    @Test
    public void canUpdateWatchList(){
        WatchList watchList = new WatchList(symbols);

        HttpResponse<Object> result = client.toBlocking().exchange(PUT("/watchlist", watchList));
        assertEquals(HttpStatus.OK, result.getStatus());
        assertEquals(watchList.getSymbols(), store.getWatchList(TEST_ACCOUNT_ID).getSymbols());
    }

    @Test
    public void canDeleteWatchList(){
        WatchList watchList = new WatchList(symbols);
        store.updateWatchList(TEST_ACCOUNT_ID, watchList);
        assertFalse(store.getWatchList(TEST_ACCOUNT_ID).getSymbols().isEmpty());

        HttpResponse<Object> deleted = client.toBlocking().exchange(DELETE("/watchlist/" + TEST_ACCOUNT_ID));
        assertEquals(HttpStatus.OK, deleted.getStatus());
        assertTrue(store.getWatchList(TEST_ACCOUNT_ID).getSymbols().isEmpty());

    }
}
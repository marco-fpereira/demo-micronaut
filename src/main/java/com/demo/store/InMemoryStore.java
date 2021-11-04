package com.demo.store;

import com.demo.model.Quote;
import com.demo.model.Symbol;
import com.demo.model.WatchList;
import jakarta.inject.Singleton;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class InMemoryStore {

    private final List<Symbol> symbols;
    private final Map<String, Quote> cachedQuotes = new HashMap<>();
    private final Map<UUID, WatchList> watchListMap =  new HashMap<>();

    public InMemoryStore() {
        this.symbols = Stream.of("APPL", "AMZN", "FB", "GOOG", "MSFT", "NFLX", "TSLA")
                .map(Symbol::new).collect(Collectors.toList());

        this.symbols.forEach(symbol ->
            cachedQuotes.put(symbol.getValue(), randomQuote(symbol))
        );
    }

    public List<Symbol> getAllSymbols(){
        return symbols;
    }

    public Optional<Quote> fetchQuote(String symbol) {
        return Optional.ofNullable(cachedQuotes.get(symbol));
    }

    private BigDecimal randomValue(){
        return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1,100));
    }

    private Quote randomQuote(Symbol symbol){
        return new Quote(symbol, randomValue(), randomValue(), randomValue(), randomValue());
    }

    public void updateQuote(Quote quote){
        cachedQuotes.put(quote.getSymbol().getValue(), quote);
    }


    public WatchList getWatchList(UUID accountId) {
        return watchListMap.getOrDefault(accountId, new WatchList());
    }

    public WatchList updateWatchList(UUID accountId, WatchList watchList) {
        watchListMap.put(accountId, watchList);
        return getWatchList(accountId);
    }

    public void deleteWatchList(UUID accountId) {
        watchListMap.remove(accountId);
    }
}

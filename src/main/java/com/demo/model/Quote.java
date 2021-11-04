package com.demo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Quote implements Serializable {

    private Symbol symbol;
    private BigDecimal bid;
    private BigDecimal ask;
    private BigDecimal lastPrice;
    private BigDecimal volume;

    public Quote() {
    }

    public Quote(Symbol symbol, BigDecimal bid, BigDecimal ask, BigDecimal lastPrice, BigDecimal volume) {
        this.symbol = symbol;
        this.bid = bid;
        this.ask = ask;
        this.lastPrice = lastPrice;
        this.volume = volume;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quote quote = (Quote) o;
        return Objects.equals(symbol, quote.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
}

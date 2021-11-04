package com.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WatchList {

    private List<Symbol> symbols = new ArrayList<>();

    public WatchList() {}

    public WatchList(List<Symbol> symbols) {
        this.symbols = symbols;
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<Symbol> symbols) {
        this.symbols = symbols;
    }

}

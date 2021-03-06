package com.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class Symbol implements Serializable {
    private String value;

    public Symbol(){}

    public Symbol(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol = (Symbol) o;
        return Objects.equals(value, symbol.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "value='" + value + '\'' +
                '}';
    }
}

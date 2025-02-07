package com.gto.gtocore.utils;

import lombok.Getter;

import java.util.function.BiFunction;

public class FunctionContainer<T, U> {

    @Getter
    private T value;
    private final BiFunction<T, U, T> function;

    public FunctionContainer(T t, BiFunction<T, U, T> function) {
        value = t;
        this.function = function;
    }

    public void apply(U u) {
        value = function.apply(value, u);
    }
}

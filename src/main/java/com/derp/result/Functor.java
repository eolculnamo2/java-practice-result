package com.derp.result;


import java.util.function.Function;

public interface Functor<T> {
    <R> Functor<R> map(Function<T, R> mapper);
}

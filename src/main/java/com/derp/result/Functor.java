package com.derp.result;


@FunctionalInterface
 interface Mapper<T, R> {
    R map(T value);
}
public interface Functor<T> {
    <R> Functor<R> map(Mapper<T, R> mapper);
}

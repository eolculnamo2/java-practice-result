package com.derp.result;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResultTest {
    @Test
    public void testOfOk() {
        var result = Result.ok("foo");
        var value = result
                .map(String::length)
                .get()
                .orElseThrow();
        Assertions.assertEquals(3, value);

    }

    @Test
    public void testOfErr() {
        Result<Integer, String> result = Result.err("foo");
        var value = result
                .map(s -> s)
                .get()
                .orElse(42);
        Assertions.assertEquals(42, value);

        Result<Integer, String> result2 = Result.err("foo");
        var value2 = result
                .mapErr(String::length)
                .getErr()
                .orElseThrow();
        Assertions.assertEquals(3, value2);
    }

    @Test
    public void testGetOr() {
       Result<Integer, String> result = Result.ok(10);
       Assertions.assertEquals(10, result.getOr(42));

       Result<Integer, String> result2 = Result.err("10");
       Assertions.assertEquals(42, result2.getOr(42));
    }

    @Test
    public void testGetOrElse() {
        Result<Integer, String> result = Result.ok(10);
        Assertions.assertEquals(10, result.getOrElse(String::length));

        Result<Integer, String> result2 = Result.err("10");
        Assertions.assertEquals(2, result2.getOrElse(String::length));
    }

    @Test
    public void testFlatMap() {
        Result<Integer, String> result = Result.ok(10);
        Assertions.assertEquals(20, result
                .flatMap(x -> Result.ok(x + 10))
                .get()
                .orElseThrow());

        Result<Integer, String> result2 = Result.err("foo");
        Assertions.assertTrue(result2
                .flatMap(x -> Result.ok(x + 10))
                .get()
                .isEmpty());
    }
}

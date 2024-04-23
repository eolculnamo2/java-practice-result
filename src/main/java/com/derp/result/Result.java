package com.derp.result;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
interface FlatMap<OldOk, NewOk, Err> {
    Result<NewOk, Err> flatMap(OldOk ok);
}

public class Result<Ok, Err> implements Functor<Ok> {
    private Ok okVal;
    private Err errVal;

    public static <Ok, Err> Result<Ok, Err> ok(final Ok ok) {
        var r = new Result<Ok, Err>();
        r.okVal = ok;
        return r;
    }

    public static <Ok, Err> Result<Ok, Err> err(final Err err) {
        var r = new Result<Ok, Err>();
        r.errVal = err;
        return r;
    }

    public static <Ok> Result<Ok, Throwable> fromThrowable(final Supplier<Ok> supplier) {
        try {
            return Result.ok(supplier.get());
        } catch(Throwable e) {
            return Result.err(e);
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public <R> Result<R, Err> map(final Function<Ok, R> mapper) {
        if (this.okVal != null) {
            return Result.ok(mapper.apply(okVal));
        }
        return (Result<R, Err>) this;
    }

    @SuppressWarnings("unchecked")
    public <R> Result<Ok, R> mapErr(final Function<Err, R> mapper) {
        if (this.errVal != null) {
            return Result.err(mapper.apply(errVal));
        }
        return (Result<Ok, R>) this;
    }

    public Optional<Ok> get() {
        if (okVal != null) {
            return Optional.of(okVal);
        }
        return Optional.empty();
    }

    public Optional<Err> getErr() {
        if (errVal != null) {
            return Optional.of(errVal);
        }
        return Optional.empty();
    }

    public Ok getOr(final Ok fallback) {
        if (okVal == null) {
            return fallback;
        }
        return okVal;
    }

    public Ok getOrElse(final Function<Err, Ok> fallback) {
        if (errVal != null) {
            return fallback.apply(errVal);
        }
        return okVal;
    }

    @SuppressWarnings("unchecked")
    public <NewOk> Result<NewOk, Err> flatMap(final FlatMap<Ok, NewOk, Err> fallback) {
        if (errVal != null) {
            return (Result<NewOk, Err>) this;
        }
        return fallback.flatMap(okVal);
    }
}

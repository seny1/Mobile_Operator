package org.elSasen.mapper;

public interface Mapper<F, T> {
    T mapFrom(F object);
}

package com.fpmislata.basespring.controller.common.base;

public interface Mapper<E, C, D> {
    C toCollection(E entity);
    D toDetail(E entity);
}

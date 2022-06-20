package com.revoltcode.cqrs.core.infrastructure.handler;

import com.revoltcode.cqrs.core.domain.aggregate.AggregateRoot;

public interface EventSourcingHandler<T> {
    void save(AggregateRoot aggregate);
    T getById(String id);
    void republishEvents();
}

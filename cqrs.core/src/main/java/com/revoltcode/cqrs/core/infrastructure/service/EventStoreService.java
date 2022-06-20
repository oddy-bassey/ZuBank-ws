package com.revoltcode.cqrs.core.infrastructure.service;

import com.revoltcode.cqrs.core.event.BaseEvent;

import java.util.List;

public interface EventStoreService {

    void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion);
    List<BaseEvent> getEvents(String aggregateId);
    List<String> getAggregateIds();
}

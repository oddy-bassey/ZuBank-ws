package com.revoltcode.account.cmd.infrastructure.service;

import com.revoltcode.account.cmd.domain.aggregate.AccountAggregate;
import com.revoltcode.account.cmd.domain.repository.EventStoreRepository;
import com.revoltcode.cqrs.core.event.BaseEvent;
import com.revoltcode.cqrs.core.event.EventModel;
import com.revoltcode.cqrs.core.exception.AggregateNotFoundException;
import com.revoltcode.cqrs.core.exception.ConcurrencyException;
import com.revoltcode.cqrs.core.exception.EventStreamNotFoundException;
import com.revoltcode.cqrs.core.infrastructure.service.EventStoreService;
import com.revoltcode.cqrs.core.infrastructure.producer.EventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountEventStoreServiceImpl implements EventStoreService {

    @Autowired
    private final EventProducer eventProducer;
    @Autowired
    private final EventStoreRepository eventStoreRepository;

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);

        // implementing optimistic concurrency control
        if(expectedVersion != -1 && eventStream.get(eventStream.size()-1).getVersion() != expectedVersion){
            throw new ConcurrencyException("concurrency exception occurred");
        }

        var version = expectedVersion;
        for(var event : events){
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .createdDate(LocalDateTime.now())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();
            var persistedEvent = eventStoreRepository.save(eventModel);
            if(!persistedEvent.getId().isEmpty()){
                // TODO: produce event to kafka
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if(eventStream == null && eventStream.isEmpty()){
            throw new AggregateNotFoundException("Incorrect account ID provided!");
        }
        return eventStream.stream().map(x -> x.getEventData()).collect(Collectors.toList());
    }

    @Override
    public List<String> getAggregateIds() {
        var eventStream = eventStoreRepository.findAll();
        if(eventStream.isEmpty()) throw new EventStreamNotFoundException("Could not retrieve the event stream from event store!");
        return eventStream.stream().map(EventModel::getAggregateIdentifier).distinct().collect(Collectors.toList());
    }
}

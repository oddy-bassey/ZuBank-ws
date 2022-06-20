package com.revoltcode.account.cmd.infrastructure.handler;
import com.revoltcode.account.cmd.domain.aggregate.AccountAggregate;
import com.revoltcode.cqrs.core.domain.aggregate.AggregateRoot;
import com.revoltcode.cqrs.core.infrastructure.handler.EventSourcingHandler;
import com.revoltcode.cqrs.core.infrastructure.producer.EventProducer;
import com.revoltcode.cqrs.core.infrastructure.service.EventStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@RequiredArgsConstructor
@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    @Autowired
    private final EventStoreService eventStoreService;

    @Autowired
    private final EventProducer eventProducer;

    @Override
    public void save(AggregateRoot aggregate) {
        eventStoreService.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate = new AccountAggregate();
        var events = eventStoreService.getEvents(id);
        if(events != null && !events.isEmpty()){
            aggregate.replayEvent(events);
            var latestVersion = events.stream().map(x -> x.getVersion()).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }
        return aggregate;
    }

    @Override
    public void republishEvents() {
        var aggregateIds = eventStoreService.getAggregateIds();
        for(var aggregateId : aggregateIds){
            var aggregate = getById(aggregateId);
            if(aggregate == null || !aggregate.getActive()) continue;
            var events = eventStoreService.getEvents(aggregateId);
            for(var event : events){
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }
}

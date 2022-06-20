package com.revoltcode.cqrs.core.infrastructure.producer;

import com.revoltcode.cqrs.core.event.BaseEvent;

public interface EventProducer {
    void produce(String topic, BaseEvent event);
}

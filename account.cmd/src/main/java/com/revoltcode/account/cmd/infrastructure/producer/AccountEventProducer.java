package com.revoltcode.account.cmd.infrastructure.producer;

import com.revoltcode.cqrs.core.event.BaseEvent;
import com.revoltcode.cqrs.core.infrastructure.producer.EventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountEventProducer implements EventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produce(String topic, BaseEvent event) {
        this.kafkaTemplate.send(topic, event);
    }
}

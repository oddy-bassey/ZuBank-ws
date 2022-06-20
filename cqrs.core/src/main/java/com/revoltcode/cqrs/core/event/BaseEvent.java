package com.revoltcode.cqrs.core.event;

import com.revoltcode.cqrs.core.message.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseEvent extends Message {
    /*
     * the version is important when we replay the event store to recreate the state of the aggregate
     * it also enables us implement optimistic concurrency control
     */
    private int version;
}

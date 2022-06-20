package com.revoltcode.account.common.event.account;

import com.revoltcode.cqrs.core.event.BaseEvent;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class AccountClosedEvent extends BaseEvent {
}

package com.revoltcode.account.query.infrastructure.handler;

import com.revoltcode.account.query.query.*;
import com.revoltcode.cqrs.core.domain.model.BaseEntity;

import java.util.List;

public interface QueryHandler {
    List<BaseEntity> handle(FindAllAccountQuery query);
    List<BaseEntity> handle(FindAccountByIdQuery query);
    List<BaseEntity> handle(FindAccountByCustomerIdQuery query);
    List<BaseEntity> handle(FindAccountByCustomerIdAndAccountTypeQuery query);
    List<BaseEntity> handle(GetAccountCountQuery query);
}

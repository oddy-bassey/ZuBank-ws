package com.revoltcode.cqrs.core.infrastructure.dispatcher;
import com.revoltcode.cqrs.core.domain.model.BaseEntity;
import com.revoltcode.cqrs.core.query.BaseQuery;
import com.revoltcode.cqrs.core.query.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);
    <U extends BaseEntity> List<U> send(BaseQuery query);
}

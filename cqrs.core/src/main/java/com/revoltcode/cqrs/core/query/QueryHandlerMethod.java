package com.revoltcode.cqrs.core.query;

import com.revoltcode.cqrs.core.domain.model.BaseEntity;

import java.util.List;

@FunctionalInterface
public interface QueryHandlerMethod<T extends BaseQuery> {
    List<BaseEntity> handle(T query);
}

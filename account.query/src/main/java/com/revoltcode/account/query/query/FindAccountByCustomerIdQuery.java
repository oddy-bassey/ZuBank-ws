package com.revoltcode.account.query.query;

import com.revoltcode.cqrs.core.query.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountByCustomerIdQuery extends BaseQuery {
    private String customerId;
}

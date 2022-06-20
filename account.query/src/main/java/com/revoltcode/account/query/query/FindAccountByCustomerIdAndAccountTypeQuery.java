package com.revoltcode.account.query.query;

import com.revoltcode.account.common.dto.AccountType;
import com.revoltcode.cqrs.core.query.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountByCustomerIdAndAccountTypeQuery extends BaseQuery {
    private String customerId;
    private AccountType accountType;
}

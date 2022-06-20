package com.revoltcode.account.query.domain.dto;

import com.revoltcode.cqrs.core.domain.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCount extends BaseEntity {
    long numberOfAccounts;
}

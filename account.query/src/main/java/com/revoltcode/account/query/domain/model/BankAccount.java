package com.revoltcode.account.query.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.revoltcode.account.common.dto.AccountType;
import com.revoltcode.cqrs.core.domain.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BankAccount extends BaseEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String id;

    @NotEmpty
    @Column
    private String customerId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column
    private AccountType accountType;

    @NotNull
    @Column
    private BigDecimal balance;

    @NotEmpty
    @Column
    private String name;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime lastUpdatedDate;
}




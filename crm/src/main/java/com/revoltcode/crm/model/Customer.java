package com.revoltcode.crm.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.revoltcode.crm.enumCategory.Gender;
import com.revoltcode.cqrs.core.domain.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(	name = "customer",
    uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @NotEmpty
    @Column
    private String firstName;

    @NotEmpty
    @Column
    private String lastName;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column
    private LocalDate dateOfBirth;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;

    @Email
    @Column
    private String email;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime lastUpdatedDate;
}

package com.revoltcode.crm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.revoltcode.crm.enumCategory.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {

    @NotEmpty(message = "customer firstName must be provided!")
    private String firstName;

    @NotEmpty(message = "customer lastName must be provided!")
    private String lastName;

    @NotNull(message = "gender (male/female) must be provided!")
    private Gender gender;

    @NotNull(message = "customer date of birth must be provided!")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Email
    private String email;
}

package org.monjasa.lab02.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
public class Employee {

    @NonNull
    @ToString.Exclude
    private Long id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private LocalDate dateOfBirth;

    @NonNull
    private EmployeePosition position;

    @NonNull
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private BigDecimal salary;
}

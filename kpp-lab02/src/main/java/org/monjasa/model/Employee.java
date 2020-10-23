package org.monjasa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
public class Employee {

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

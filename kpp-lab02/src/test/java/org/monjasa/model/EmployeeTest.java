package org.monjasa.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.monjasa.model.EmployeePosition.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class EmployeeTest {

    @Test
    void shouldUnmarshallListOfEmployees() throws IOException {

        List<Employee> employees = List.of(
                Employee.builder()
                        .id(1L)
                        .firstName("First")
                        .lastName("Employee")
                        .dateOfBirth(LocalDate.of(1991, Month.MARCH, 11))
                        .position(CASHIER)
                        .salary(new BigDecimal("25000"))
                        .build(),
                Employee.builder()
                        .id(2L)
                        .firstName("Second")
                        .lastName("Employee")
                        .dateOfBirth(LocalDate.of(2000, Month.DECEMBER, 31))
                        .position(MANAGER)
                        .salary(new BigDecimal("125000"))
                        .build()
        );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        URL source = EmployeeTest.class.getClassLoader().getResource("marshalled.json");
        List<Employee> unmarshalledEmployees = Arrays.asList(
                objectMapper.readValue(source, Employee[].class)
        );

        assertThat(unmarshalledEmployees).isNotNull()
                .isEqualTo(employees);
    }
}
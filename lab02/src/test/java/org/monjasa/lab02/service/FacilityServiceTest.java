package org.monjasa.lab02.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.monjasa.lab02.model.Employee;
import org.monjasa.lab02.model.EmployeePosition;
import org.monjasa.lab02.model.Facility;
import org.monjasa.lab02.model.SalaryBracket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class FacilityServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(FacilityServiceTest.class);

    private Facility facility;

    @BeforeEach
    void setUp() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        URL source = FacilityServiceTest.class.getClassLoader().getResource("employees.json");
        List<Employee> unmarshalledEmployees = Arrays.asList(
                objectMapper.readValue(source, Employee[].class)
        );

        facility = new Facility(unmarshalledEmployees);
    }

    @Test
    void shouldGroupEmployeesLastNamesByPosition() {

        Map<EmployeePosition, List<String>> result = FacilityService.groupEmployeesLastNamesByPosition(facility);
        result.forEach((key, value) -> logger.info(key + " => " + value));

        assertThat(result).isNotNull();
    }

    @Test
    void shouldGroupYoungestAndOldestEmployeesByPosition() {

        Map<EmployeePosition, FacilityStatistics<Employee>> result = FacilityService.groupYoungestAndOldestEmployeeByPosition(facility);
        result.forEach((key, value) -> logger.info(key + " => " + value));

        assertThat(result).isNotNull();
    }

    @Test
    void shouldGroupEmployeesBySalary() {

        Map<SalaryBracket, List<Employee>> result = FacilityService.groupEmployeesBySalaryBracket(facility);
        result.forEach((key, value) -> logger.info(key + " => " + value));

        assertThat(result).isNotNull();
    }
}
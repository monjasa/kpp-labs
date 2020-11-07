package org.monjasa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.monjasa.model.Employee;
import org.monjasa.model.EmployeePosition;
import org.monjasa.model.Facility;
import org.monjasa.model.SalaryBracket;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public final class FacilityService {

    private FacilityService() {
    }

    public static FacilityStatistics<BigDecimal> getSalaryStatistics(Facility facility) {
        return facility.getEmployees().stream()
                .map(Employee::getSalary)
                .collect(FacilityStatistics.collector(BigDecimal::compareTo));
    }

    public static List<Employee> excludeEmployeesBornAfterDate(Facility facility, LocalDate date) {
        return facility.getEmployees().stream()
                .filter(employee -> employee.getDateOfBirth().isBefore(date))
                .collect(Collectors.toList());
    }

    public static Map<EmployeePosition, List<String>> groupEmployeesLastNamesByPosition(Facility facility) {
        return facility.getEmployees().stream()
                .collect(groupingBy(
                        Employee::getPosition,
                        mapping(Employee::getLastName, toList())
                ));
    }

    public static Map<EmployeePosition, FacilityStatistics<Employee>> groupYoungestAndOldestEmployeeByPosition(Facility facility) {
        return facility.getEmployees().stream()
                .collect(groupingBy(
                        Employee::getPosition,
                        FacilityStatistics.collector(Comparator.comparing(Employee::getDateOfBirth))
                ));
    }

    public static Map<SalaryBracket, List<Employee>> groupEmployeesBySalaryBracket(Facility facility) {

        FacilityStatistics<BigDecimal> salaryStatistics = getSalaryStatistics(facility);

        BigDecimal maxSalary = salaryStatistics.getMaxValue();
        BigDecimal minSalary = salaryStatistics.getMinValue();

        List<SalaryBracket> salaryBrackets = List.of(SalaryBracket.values());
        BigDecimal bracketWidth = maxSalary.subtract(minSalary)
                .divide(BigDecimal.valueOf(salaryBrackets.size()), RoundingMode.HALF_EVEN);

        Function<Employee, SalaryBracket> salaryBracketClassifier = employee -> {
            int index = employee.getSalary().compareTo(maxSalary) == 0
                    ? salaryBrackets.size() - 1
                    : employee.getSalary().subtract(minSalary).divide(bracketWidth, RoundingMode.FLOOR).intValue();
            return salaryBrackets.get(index);
        };

        return facility.getEmployees().stream()
                .collect(groupingBy(salaryBracketClassifier));
    }

    public static <T> Predicate<Employee> distinctEmployeeByKey(Function<Employee, T> keyExtractor) {
        Set<T> uniqueKeys = ConcurrentHashMap.newKeySet();
        return employee -> uniqueKeys.add(keyExtractor.apply(employee));
    }

    public static Facility unmarshallFacility(File... files) {
        return unmarshallFacility(Function.identity(), files);
    }

    public static Facility unmarshallFacility(Function<Employee, ?> keyExtractor, File... files) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        List<Employee> unmarshalledEmployees = Arrays.stream(files)
                .flatMap(file -> {
                    try {
                        return Arrays.stream(objectMapper.readValue(file, Employee[].class));
                    } catch (IOException exception) {
                        throw new UncheckedIOException(exception);
                    }
                }).filter(distinctEmployeeByKey(keyExtractor))
                .collect(toList());

        return new Facility(unmarshalledEmployees);
    }
}

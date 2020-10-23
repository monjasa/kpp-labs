package org.monjasa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.ToString;
import org.monjasa.model.Employee;
import org.monjasa.model.EmployeePosition;
import org.monjasa.model.Facility;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class FacilityHandler {

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
                        FacilityStatistics.statistics(Comparator.comparing(Employee::getDateOfBirth))
                ));
    }

    public static Map<BigDecimal, List<Employee>> groupEmployeesBySalary(Facility facility) {
        return facility.getEmployees().stream()
                .collect(groupingBy(
                        employee -> employee.getSalary().divide(new BigDecimal(10000), RoundingMode.HALF_EVEN).setScale(0, RoundingMode.CEILING),
                        Collectors.toList()
                ));
    }

    public static Facility unmarshallFacilityFromFile(File file) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        List<Employee> unmarshalledEmployees = new ArrayList<>();

        try {
            unmarshalledEmployees = Arrays.asList(objectMapper.readValue(file, Employee[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Facility(unmarshalledEmployees);
    }

    @ToString
    public static class FacilityStatistics<T> implements Consumer<T> {

        @Getter
        @ToString.Exclude
        private final Comparator<T> comparator;

        @Getter
        private T minValue;

        @Getter
        private T maxValue;

        public FacilityStatistics(Comparator<T> comparator) {
            this.comparator = Objects.requireNonNull(comparator);
        }

        public static <T> Collector<T, ?, FacilityStatistics<T>> statistics(Comparator<T> comparator) {
            return Collector.of(
                    () -> new FacilityStatistics<>(comparator),
                    FacilityStatistics<T>::accept,
                    FacilityStatistics<T>::combine
            );
        }

        @Override
        public void accept(T currentValue) {
            if (minValue == null && maxValue == null) {
                minValue = currentValue;
                maxValue = currentValue;
            } else {
                if (comparator.compare(minValue, currentValue) > 0) minValue = currentValue;
                if (comparator.compare(maxValue, currentValue) < 0) maxValue = currentValue;
            }
        }

        public FacilityStatistics<T> combine(FacilityStatistics<T> statistics) {

            if (statistics.minValue != null && statistics.maxValue != null) {
                if (minValue == null && maxValue == null) {
                    minValue = statistics.minValue;
                    maxValue = statistics.maxValue;
                } else {
                    if (comparator.compare(minValue, statistics.minValue) > 0) minValue = statistics.minValue;
                    if (comparator.compare(maxValue, statistics.maxValue) < 0) maxValue = statistics.maxValue;
                }
            }

            return this;
        }
    }
}

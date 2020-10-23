package org.monjasa.model;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class Facility {
    @NonNull
    private List<Employee> employees;
}

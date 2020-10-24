package org.monjasa.model;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.util.List;

@Data
public class Facility implements Serializable {
    @NonNull
    private List<Employee> employees;
}

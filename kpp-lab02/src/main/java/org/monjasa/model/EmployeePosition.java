package org.monjasa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EmployeePosition {
    @JsonProperty("President") PRESIDENT,
    @JsonProperty("Vice President") VICE_PRESIDENT,
    @JsonProperty("Director") DIRECTOR,
    @JsonProperty("Auditor") AUDITOR,
    @JsonProperty("Manager") MANAGER,
    @JsonProperty("Operator") OPERATOR,
    @JsonProperty("Clerk") CLERK,
    @JsonProperty("Cashier") CASHIER
}

package ua.kpi.apeps.repository.rmi;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ServiceRegistry {

    public static final String EMPLOYEE_REPOSITORY = "EmployeeService";

    public static final String EMPLOYEE_SHIFT_RECORD_REPOSITORY = "ShirtRecordRepository";
}

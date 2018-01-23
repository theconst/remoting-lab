package ua.kpi.apeps.repository;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ServiceRegistry {

    public static final String EMPLOYEE_REPOSITORY = "EmployeeRepository";

    public static final String TRANSACTION = "Transaction";

    public static final String EMPLOYEE_SHIFT_RECORD_REPOSITORY = "ShiftRecordRepository";
}

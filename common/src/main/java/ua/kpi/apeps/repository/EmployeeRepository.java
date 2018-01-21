package ua.kpi.apeps.repository;

import ua.kpi.apeps.model.Employee;

public interface EmployeeRepository extends Repository<Employee, Integer> {

    String NAME = EmployeeRepository.class.getName();
}

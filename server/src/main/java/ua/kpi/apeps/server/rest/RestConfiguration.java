package ua.kpi.apeps.server.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.kpi.apeps.model.Employee;
import ua.kpi.apeps.model.EmployeeShiftRecord;
import ua.kpi.apeps.repository.Repository;
import ua.kpi.apeps.repository.rmi.EmployeeRepositoryStub;
import ua.kpi.apeps.repository.rmi.EmployeeShiftRecordRepositoryStub;

@Configuration
public class RestConfiguration {

    @Bean
    public Repository<Employee, Integer> employeeRepository() {
        return new EmployeeRepositoryStub();
    }

    @Bean
    public Repository<EmployeeShiftRecord, Integer> employeeShiftRecordRepository() {
        return new EmployeeShiftRecordRepositoryStub();
    }
}

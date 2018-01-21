package ua.kpi.apeps.server.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.kpi.apeps.model.Employee;
import ua.kpi.apeps.repository.Repository;
import ua.kpi.apeps.repository.rmi.EmployeeRepositoryFactory;

import static ua.kpi.apeps.repository.rmi.EmployeeRepositoryFactory.Modes.STUB;

@Configuration
public class RestConfiguration {

    @Bean
    public Repository<Employee, Integer> employeeRepository() {
        return EmployeeRepositoryFactory.createRepository(STUB);
    }
}

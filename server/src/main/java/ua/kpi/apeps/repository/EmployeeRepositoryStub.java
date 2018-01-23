package ua.kpi.apeps.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ua.kpi.apeps.model.Employee;

import java.util.Collection;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toList;

@Component
@Qualifier("employee-repository")
@ConditionalOnProperty(value = "stub-mode", havingValue = "true")
public class EmployeeRepositoryStub implements Repository<Employee, Integer> {

    @Override
    public Iterable<Employee> getAll() {
        return getByIds(asList(1, 2, 3));
    }

    @Override
    public Employee getById(Integer id) {
        return getByIds(singleton(id)).iterator().next();
    }

    private Collection<Employee> getByIds(Collection<Integer> integers) {
        return integers.stream()
                .map(id -> Employee.builder()
                        .name("name_" + id)
                        .surname("surname_" + id)
                        .id(id)
                        .build())
                .collect(toList());
    }

    @Override
    public Integer create(Employee employee) {
        System.out.println("Creating...");
        return employee.getId();
    }

    @Override
    public int delete(Integer integers) {
        System.out.println("Deleting...");
        return 0;
    }
}

package ua.kpi.apeps.repository.rmi;

import ua.kpi.apeps.model.Employee;
import ua.kpi.apeps.repository.EmployeeRepository;

import java.io.IOException;
import java.util.Collection;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class EmployeeRepositoryStub implements EmployeeRepository {

    public static final String NAME = "";

    @Override
    public Collection<Employee> getAll() throws IOException {
        return getByIds(asList(1, 2, 3));
    }

    @Override
    public Collection<Employee> getByIds(Collection<Integer> integers) throws IOException {
        return integers.stream()
                .map(id -> Employee.builder()
                        .name("name_" + id)
                        .surname("surname_" + id)
                        .id(id)
                        .build())
                .collect(toList());
    }

    @Override
    public Collection<Integer> create(Collection<Employee> entities) throws IOException {
        System.out.println("Creating...");
        return entities.stream()
                .map(Employee::getId)
                .collect(toList());
    }

    @Override
    public int delete(Collection<Integer> integers) throws IOException {
        System.out.println("Deleting...");
        return 0;
    }
}

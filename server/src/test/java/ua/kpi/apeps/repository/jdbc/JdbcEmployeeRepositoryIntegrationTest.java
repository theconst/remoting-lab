package ua.kpi.apeps.repository.jdbc;


import org.junit.Ignore;
import org.junit.Test;
import ua.kpi.apeps.model.Employee;
import ua.kpi.apeps.model.EmployeeShiftRecord;
import ua.kpi.apeps.repository.Repository;

import java.time.LocalDateTime;

import static junit.framework.Assert.assertNull;
import static ua.kpi.apeps.repository.jdbc.JdbcTransactionTemplate.singleJdbcTransaction;

//This is an itegration test that just pollutes the console
@Ignore
public class JdbcEmployeeRepositoryIntegrationTest {

    @Test
    public void shouldConnectToTheDataSource() {
        Repository<Employee, Integer> employeeRepository
                = new JdbcEmployeeRepository();

        Repository<EmployeeShiftRecord, Integer> employeeShiftRecordRepository
                = new JdbcEmployeeShiftRecordRepository();


        final Integer id = singleJdbcTransaction(() ->
                employeeRepository.create(Employee.builder()
                        .name("Ivan")
                        .surname("Petrov")
                        .occupation("engineer")
                        .post("lead")
                        .since(LocalDateTime.now())
                        .build())
        );

        assertNull(id);

        singleJdbcTransaction(() -> {
            Iterable<Employee> employees = employeeRepository.getAll();

            System.out.println(employees);

            singleJdbcTransaction(() -> {
                System.out.println("Just a nesting test");
                employees.forEach(employee -> {
                    employeeShiftRecordRepository.create(
                            EmployeeShiftRecord.builder()
                                    .comment("Signature of " + employee.getSurname())
                                    .wasControlled(true)
                                    .workedFrom(LocalDateTime.now())
                                    .workedTo(LocalDateTime.now().plusDays(1))
                                    .employeeId(employee.getId())
                                    .build()
                    );
                });
                return null;
            });

            return null;
        });
    }

}

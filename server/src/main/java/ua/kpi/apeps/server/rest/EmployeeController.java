package ua.kpi.apeps.server.rest;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.apeps.model.Employee;
import ua.kpi.apeps.model.EmployeeShiftRecord;
import ua.kpi.apeps.repository.Repository;

import static ua.kpi.apeps.repository.jdbc.JdbcTransactionTemplate.singleJdbcTransaction;

@RestController
@RequestMapping("employees/")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EmployeeController {

    private Repository<Employee, Integer> employeeRepository;

    private Repository<EmployeeShiftRecord, Integer> employeeShiftRecordRepository;

    @GetMapping
    public Iterable<Employee> getAllEmployees() {
        return employeeRepository.getAll();
    }

    @GetMapping("{id}")
    public Employee getById(@PathVariable("id") Integer id) {
        return singleJdbcTransaction(() -> employeeRepository.getById(id));
    }

    @GetMapping("shifts")
    public Iterable<EmployeeShiftRecord> getAllShiftRecords() {
        return singleJdbcTransaction(() -> employeeShiftRecordRepository.getAll());
    }

}

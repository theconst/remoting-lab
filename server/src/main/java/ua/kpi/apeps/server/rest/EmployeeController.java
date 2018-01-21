package ua.kpi.apeps.server.rest;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.apeps.model.Employee;
import ua.kpi.apeps.repository.Repository;

@RestController
@RequestMapping("employees/")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EmployeeController {

    private Repository<Employee, Integer> repository;

    @GetMapping
    @SneakyThrows
    public Iterable<Employee> getAllEmployees() {
        return repository.getAll();
    }

    @GetMapping("{id}")
    @SneakyThrows
    public Employee getById(@PathVariable("id") Integer id) {
        return repository.getById(id);
    }
}

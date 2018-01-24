package ua.kpi.apeps.server.rmi;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.kpi.apeps.model.Employee;
import ua.kpi.apeps.model.EmployeeShiftRecord;
import ua.kpi.apeps.repository.RemoteEmployeeBatchUpdateService;
import ua.kpi.apeps.repository.Repository;

import java.rmi.RemoteException;

import static ua.kpi.apeps.repository.jdbc.JdbcTransactionTemplate.singleJdbcTransaction;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RemoteEmployeeBatchUpdateServiceImpl implements RemoteEmployeeBatchUpdateService {

    private Repository<Employee, Integer> employeeRepository;

    private Repository<EmployeeShiftRecord, Integer> employeeShiftRecordRepository;

    @Override
    public void batchUpdateRecords(Iterable<EmployeeShiftRecord> records) throws RemoteException {
        singleJdbcTransaction(() -> records.forEach(r -> employeeShiftRecordRepository.create(r)));
    }

    @Override
    public void batchUpdateEmployees(Iterable<Employee> employees) throws RemoteException {
        singleJdbcTransaction(() -> employees.forEach(e -> employeeRepository.create(e)));
    }
}

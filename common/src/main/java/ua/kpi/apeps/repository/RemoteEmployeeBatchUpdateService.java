package ua.kpi.apeps.repository;

import ua.kpi.apeps.model.Employee;
import ua.kpi.apeps.model.EmployeeShiftRecord;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface that defines a contract between a client and a server
 */
public interface RemoteEmployeeBatchUpdateService extends Remote {

    String NAME = RemoteEmployeeBatchUpdateService.class.getSimpleName();

    void batchUpdateRecords(Iterable<EmployeeShiftRecord> records) throws RemoteException;

    void batchUpdateEmployees(Iterable<Employee> employees) throws RemoteException;
}

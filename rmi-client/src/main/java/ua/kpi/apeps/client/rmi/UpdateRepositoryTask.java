package ua.kpi.apeps.client.rmi;

import lombok.AllArgsConstructor;
import ua.kpi.apeps.model.EmployeeShiftRecord;
import ua.kpi.apeps.repository.RemoteEmployeeBatchUpdateService;

import java.util.Collection;
import java.util.concurrent.Callable;

/**
 * Task that updates repository.
 * Can be executed both synchronously and asynchronously
 */
@AllArgsConstructor(staticName = "of")
public class UpdateRepositoryTask implements Callable<Void> {

    private final String fileName;

    private RemoteEmployeeBatchUpdateService service;


    @Override
    public Void call() throws Exception {
        //use remote batching service to update records
        try (EmployeeShiftRecordCSVReader csvRepository = new EmployeeShiftRecordCSVReader(fileName)) {
            Collection<EmployeeShiftRecord> newRecords = csvRepository.readAll();
            service.batchUpdateRecords(newRecords);
        }
        return null;
    }
}

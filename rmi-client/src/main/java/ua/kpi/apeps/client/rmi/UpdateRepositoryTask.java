package ua.kpi.apeps.client.rmi;

import lombok.AllArgsConstructor;
import ua.kpi.apeps.model.EmployeeShiftRecord;

import java.util.Collection;
import java.util.concurrent.Callable;

/**
 * Task that updates repository.
 * Can be executed both synchronously and asynchronously
 */
@AllArgsConstructor(staticName = "of")
public class UpdateRepositoryTask implements Callable<Void> {

    private final String fileName;

    private final RemoteRepository<EmployeeShiftRecord, Integer> remoteRepository;

    private final RemoteTransactionTemplate<Void> remoteTransaction;


    @Override
    public Void call() throws Exception {
        //flush data from one source to another
        try (EmployeeShiftRecordCSVRepository csvRepository = new EmployeeShiftRecordCSVRepository(fileName)) {
            Collection<EmployeeShiftRecord> newRecords = csvRepository.getAll();

            remoteTransaction.doInTransaction(() -> {
                for (EmployeeShiftRecord r : newRecords) {
                    remoteRepository.create(r);
                }
                return null;
            });
        }
        return null;
    }
}

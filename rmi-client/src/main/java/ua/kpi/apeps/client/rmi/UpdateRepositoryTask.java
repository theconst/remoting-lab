package ua.kpi.apeps.client.rmi;

import lombok.AllArgsConstructor;
import ua.kpi.apeps.model.EmployeeShiftRecord;
import ua.kpi.apeps.repository.RemoteRepository;

import java.time.LocalDateTime;
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

    private static void setJournal(Collection<EmployeeShiftRecord> newRecords, Integer journalId) {
        newRecords.forEach(record -> record.setJournalId(journalId));
    }

    @Override
    public Void call() throws Exception {
        //flush data from one source to another
        try (EmployeeShiftRecordCSVRepository csvRepository = new EmployeeShiftRecordCSVRepository(fileName)) {
            Collection<EmployeeShiftRecord> newRecords = csvRepository.getAll();
            setJournal(newRecords, LocalDateTime.now().getYear()); //journals are identified by year
            remoteRepository.create(newRecords);
        }
        return null;
    }
}

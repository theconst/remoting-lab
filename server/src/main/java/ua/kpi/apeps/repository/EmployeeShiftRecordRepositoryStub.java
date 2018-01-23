package ua.kpi.apeps.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ua.kpi.apeps.model.EmployeeShiftRecord;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Slf4j
@Component
@ConditionalOnProperty(value = "stub-mode", havingValue = "true")
public class EmployeeShiftRecordRepositoryStub implements EmployeeShiftRecordRepository {

    private static final AtomicInteger counter = new AtomicInteger();
    private final Map<Integer, EmployeeShiftRecord> recordsById = new HashMap<>();

    @Override
    public Collection<EmployeeShiftRecord> getAll() {
        return recordsById.values();
    }

    @Override
    public Collection<EmployeeShiftRecord> getByIds(Collection<Integer> ids) {
        return ids.stream()
                .map(recordsById::get)
                .collect(toList());
    }

    @Override
    public Collection<Integer> create(Collection<EmployeeShiftRecord> entities) {
        try {
            return entities.stream()
                    .map(e -> {
                        int next = counter.incrementAndGet();
                        recordsById.put(next, e);
                        return next;
                    })
                    .collect(toList());
        } finally {
            log.debug(format("Dump: %s", recordsById.entrySet()));
        }
    }

    @Override
    public int delete(Collection<Integer> ids) {
        return (int) ids.stream()
                .map(recordsById::remove)
                .filter(Objects::nonNull)
                .count();
    }

    @Override
    public Iterable<EmployeeShiftRecord> getAllEmployeeShiftRecords(Integer empId, Integer journalId) {
        return recordsById.values().stream()
                .filter(r -> empId.equals(r.getEmployeeId()))
                .filter(r -> journalId.equals(r.getJournalId()))
                .collect(toList());
    }
}

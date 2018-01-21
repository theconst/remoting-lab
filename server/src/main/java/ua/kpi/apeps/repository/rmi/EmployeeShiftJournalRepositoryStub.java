package ua.kpi.apeps.repository.rmi;

import lombok.extern.slf4j.Slf4j;
import ua.kpi.apeps.model.EmployeeShiftRecord;
import ua.kpi.apeps.repository.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Slf4j
public class EmployeeShiftJournalRepositoryStub implements Repository<EmployeeShiftRecord, Integer> {

    private final Map<Integer, EmployeeShiftRecord> recordsById = new HashMap<>();

    private static final AtomicInteger counter = new AtomicInteger();

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
    public int delete(Collection<Integer> integers) {
        return (int) integers.stream()
                .map(recordsById::remove)
                .filter(Objects::nonNull)
                .count();
    }
}

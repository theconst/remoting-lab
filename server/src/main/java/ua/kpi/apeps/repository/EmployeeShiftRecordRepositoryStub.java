package ua.kpi.apeps.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ua.kpi.apeps.model.EmployeeShiftRecord;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.String.format;

@Slf4j
@Component
@Qualifier("employee-shift-record-repository")
@ConditionalOnProperty(value = "stub-mode", havingValue = "true")
public class EmployeeShiftRecordRepositoryStub
        implements Repository<EmployeeShiftRecord, Integer> {

    private static final AtomicInteger counter = new AtomicInteger();
    private final Map<Integer, EmployeeShiftRecord> recordsById = new HashMap<>();


    public EmployeeShiftRecord getById(Integer id) {
        return recordsById.get(id);
    }

    @Override
    public Collection<EmployeeShiftRecord> getAll() {
        return recordsById.values();
    }

    @Override
    public Integer create(EmployeeShiftRecord entity) {
        Integer next = counter.incrementAndGet();
        try {
            recordsById.put(next, entity);
        } finally {
            log.debug(format("Dump: %s", recordsById.entrySet()));
        }
        return next;
    }

    @Override
    public int delete(Integer id) {
        return (int) recordsById.keySet().stream()
                .map(recordsById::remove)
                .filter(Objects::nonNull)
                .count();
    }

}

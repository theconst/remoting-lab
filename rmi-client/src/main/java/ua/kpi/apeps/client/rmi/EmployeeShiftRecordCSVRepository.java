package ua.kpi.apeps.client.rmi;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ua.kpi.apeps.model.EmployeeShiftRecord;
import ua.kpi.apeps.repository.Repository;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

public class EmployeeShiftRecordCSVRepository
        implements Repository<EmployeeShiftRecord, Integer>, AutoCloseable {

    public static final CSVFormat FORMAT = CSVFormat.DEFAULT
            .withHeader()
            .withTrim();

    private final String pathToCsv;

    public EmployeeShiftRecordCSVRepository(String pathToCsv) {
        this.pathToCsv = pathToCsv;
    }

    @Override
    public Collection<EmployeeShiftRecord> getAll() {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(pathToCsv));
                CSVParser parser = new CSVParser(reader, FORMAT);
        ) {
            return streamOfRecords(parser.getRecords())
                    .map(CSVRecordToEmployeeShiftRecordConverter.of(parser.getHeaderMap()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<EmployeeShiftRecord> getByIds(Collection<Integer> ids) {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(pathToCsv));
                CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());
        ) {
            return streamOfRecords(parser.getRecords())
                    .map(CSVRecordToEmployeeShiftRecordConverter.of(parser.getHeaderMap()))
                    .filter(employeeShiftRecord -> ids.contains(employeeShiftRecord.getId()))
                    .collect(toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<Integer> create(Collection<EmployeeShiftRecord> entities) {
        throw new UnsupportedOperationException("This repository is scan-only");
    }

    @Override
    public int delete(Collection<Integer> integers) {
        throw new UnsupportedOperationException("This repository is scan-only");
    }

    private static Stream<CSVRecord> streamOfRecords(Iterable<CSVRecord> records) {
        return stream(spliteratorUnknownSize(records.iterator(), Spliterator.ORDERED | Spliterator.NONNULL), false);
    }

    @Override
    public void close() throws Exception {
        //no resources to free
    }
}

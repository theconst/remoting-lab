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

    private static Stream<CSVRecord> streamOfRecords(Iterable<CSVRecord> records) {
        return stream(spliteratorUnknownSize(records.iterator(), Spliterator.ORDERED | Spliterator.NONNULL), false);
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
    public EmployeeShiftRecord getById(Integer id) {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(pathToCsv));
                CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());
        ) {
            return streamOfRecords(parser.getRecords())
                    .map(CSVRecordToEmployeeShiftRecordConverter.of(parser.getHeaderMap()))
                    .filter(employeeShiftRecord -> id.equals(employeeShiftRecord.getId()))
                    .findFirst()
                    .orElse(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer create(EmployeeShiftRecord ignored) {
        throw new UnsupportedOperationException("This repository is scan-only");
    }

    @Override
    public int delete(Integer ignored) {
        throw new UnsupportedOperationException("This repository is scan-only");
    }

    @Override
    public void close() throws Exception {
        //no resources to free
    }
}

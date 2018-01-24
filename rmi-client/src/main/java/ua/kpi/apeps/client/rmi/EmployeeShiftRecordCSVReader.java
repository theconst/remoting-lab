package ua.kpi.apeps.client.rmi;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ua.kpi.apeps.model.EmployeeShiftRecord;

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

public class EmployeeShiftRecordCSVReader implements AutoCloseable {

    public static final CSVFormat FORMAT = CSVFormat.DEFAULT
            .withHeader()
            .withTrim();

    private final String pathToCsv;

    public EmployeeShiftRecordCSVReader(String pathToCsv) {
        this.pathToCsv = pathToCsv;
    }

    private static Stream<CSVRecord> streamOfRecords(Iterable<CSVRecord> records) {
        return stream(spliteratorUnknownSize(records.iterator(), Spliterator.ORDERED | Spliterator.NONNULL), false);
    }

    public Collection<EmployeeShiftRecord> readAll() {
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
    public void close() throws Exception {
        //nothing to free for now
    }
}

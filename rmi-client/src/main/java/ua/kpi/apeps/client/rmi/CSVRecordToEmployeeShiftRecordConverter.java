package ua.kpi.apeps.client.rmi;

import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVRecord;
import ua.kpi.apeps.model.EmployeeShiftRecord;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.lang.String.format;

@AllArgsConstructor(staticName = "of")
public class CSVRecordToEmployeeShiftRecordConverter implements Function<CSVRecord, EmployeeShiftRecord> {

    private static final Map<String, BiConsumer<String, EmployeeShiftRecord>> FILLERS = new HashMap<>();

    static {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy:HH-mm");
        FILLERS.put("employee_id", (value, employeeShiftRecord) ->
                employeeShiftRecord.setEmployeeId(Integer.parseInt(value)));
        FILLERS.put("from", (value, employeeShiftRecord) ->
                employeeShiftRecord.setWorkedFrom(LocalDateTime.parse(value, formatter)));
        FILLERS.put("to", (value, employeeShiftRecord) ->
                employeeShiftRecord.setWorkedTo(LocalDateTime.parse(value, formatter)));
        FILLERS.put("comment", (value, employeeShiftRecord) ->
                employeeShiftRecord.setComment(value));
        FILLERS.put("controlled", (value, employeeShiftRecord) ->
                employeeShiftRecord.setWasSigned("+".equals(value)));
    }

    private final Map<String, Integer> headerMap;

    public EmployeeShiftRecord apply(CSVRecord record) {
        EmployeeShiftRecord result = new EmployeeShiftRecord();
        for (Map.Entry<String, Integer> fieldNameAndNumber : headerMap.entrySet()) {
            String fieldName = fieldNameAndNumber.getKey();
            String fieldValue = record.get(fieldNameAndNumber.getValue());
            Optional.ofNullable(FILLERS.get(fieldName))
                    .orElseThrow(illegalField(fieldName))
                    .accept(fieldValue, result);
        }
        return result;
    }

    private static Supplier<IllegalStateException> illegalField(String fieldName) {
        return () -> new IllegalStateException(format("No field '%s' exists", fieldName));
    }
}

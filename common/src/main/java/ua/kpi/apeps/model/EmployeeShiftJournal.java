package ua.kpi.apeps.model;

import lombok.Data;
import lombok.Singular;

import java.io.Serializable;
import java.util.List;

@Data
public class EmployeeShiftJournal implements Serializable {

    private Integer id;

    private Integer responsibleEmployeeId;

    @Singular
    private List<EmployeeShiftRecord> records;
}

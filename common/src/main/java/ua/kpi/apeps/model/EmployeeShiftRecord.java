package ua.kpi.apeps.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class EmployeeShiftRecord implements Serializable {

    private Integer id;

    private String comment;

    private Integer employeeId;

    private Integer journalId;

    private LocalDateTime workedFrom;

    private LocalDateTime workedTo;

    private Boolean wasSigned;

}

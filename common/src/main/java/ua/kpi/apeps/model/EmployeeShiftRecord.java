package ua.kpi.apeps.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class EmployeeShiftRecord implements Serializable {

    private Integer id;

    private String comment;

    private Integer employeeId;

    private LocalDateTime workedFrom;

    private LocalDateTime workedTo;

    private Boolean wasControlled;

}

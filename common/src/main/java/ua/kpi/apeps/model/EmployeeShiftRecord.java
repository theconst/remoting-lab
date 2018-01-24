package ua.kpi.apeps.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeShiftRecord implements Serializable {

    private static final long serialVersionUID = 3L;

    private Integer id;

    private String comment;

    private Integer employeeId;

    private LocalDateTime workedFrom;

    private LocalDateTime workedTo;

    private Boolean wasControlled;

}

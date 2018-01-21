package ua.kpi.apeps.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class EmployeeContract implements Serializable {

    Integer employeeId;

    Integer managerId;

    String text;

    LocalDateTime validFrom;
}

package ua.kpi.apeps.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class EmployeeData implements Serializable {

    private byte[] passport;

    private byte[] resume;

    private byte[] photo;
}

package ua.kpi.apeps.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class Employee implements Serializable {

    private Integer id;

    private String name;

    private String surname;

    private LocalDateTime since;

    private String occupation;

    private String post;
}

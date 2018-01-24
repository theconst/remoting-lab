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
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String surname;

    private LocalDateTime since;

    private String occupation;

    private String post;
}

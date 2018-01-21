package ua.kpi.apeps.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class Client implements Serializable {

    private Integer id;

    private String name;

    private String address;

    private LocalDateTime since;

    private Integer contractId;
}

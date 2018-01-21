package ua.kpi.apeps.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Contract implements Serializable {

    private Integer id;

    private String text;

    private LocalDateTime validFrom;

    private LocalDateTime validTo;

    private BigDecimal amount;

    private Boolean isIndividual;

    private Boolean isInvalidated;

    private Integer responsibleEmployeeId;
}

package com.studies.financialmanagement.api.repositories.projections;

import com.studies.financialmanagement.api.models.EntryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class EntrySummary {

    private Long id;
    private String description;
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private BigDecimal price;
    private EntryType entryType;
    private String category;
    private String person;

}
